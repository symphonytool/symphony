#ifndef CO_SIMULATION_FRAMEWORK_LAYER_LIB_HPP
#define CO_SIMULATION_FRAMEWORK_LAYER_LIB_HPP

#include<STRING>
#include <VECTOR>
#include <boost/shared_ptr.hpp>
#include <boost/thread/mutex.hpp>
#include <ICoSimulationTransportLayer.hpp>
#include <map>
#include <boost/variant.hpp>
#include <iostream>
#include <boost/thread/condition.hpp>
#include <typeinfo> 

namespace CoSimulationFramework
{

	class CoSimulationFrameworkTypeSytemExecption : public std::runtime_error
    {
     public:
		 explicit CoSimulationFrameworkTypeSytemExecption (const std::string& what_arg):std::runtime_error(what_arg){}
    };

	class IType
	{
	public:
		virtual ~IType(){}
		virtual std::string getTypeName()const = 0;
	};

	// base class for defining CoSim classes that can be used by the framework
	// only used for getting the RTTI info for the subclass
	class CoSimulationTypeClass
	{
	  public:

		  virtual ~CoSimulationTypeClass(){}

		  virtual std::string getCMLObjectDescription() = 0;

	};

	// type safe template class, hold infomation and values of a native C++ type
	template <typename D>
	class TypePackage: public IType
	{
	  public:
		  typedef D TYPE;
		  TypePackage ():typeName(""),CMLtypeName(""),CMLValuetypeName("")
		  {
			  createCMlTypeDefinition();
		  }
		  TypePackage (const TYPE& v):typeName(""),CMLtypeName(""),CMLValuetypeName(""),value(v)
		  {
			  createCMlTypeDefinition();
		  }
		  
		  std::string typeName;
		  std::string CMLtypeName;
		  std::string CMLValuetypeName;
		  TYPE value;

          TYPE operator()() const
          {
            return value;
          }

		  std::string getTypeName()const
		  {
		    return typeName;
		  }

         // types must be serilaziable
	    friend std::ostream& operator<< (std::ostream& stream, const TypePackage<D>& obj)
	    {
		 stream <<obj.typeName<<std::endl;
		 stream <<obj.value<<std::endl;
		 return stream;
	    }

	   friend std::istream& operator>> (std::istream& stream,TypePackage<D>& obj)
	   {
		 stream >>obj.typeName;
		 stream >>obj.value;
		 return stream;
	   }

	   private:

		   void createCMlTypeDefinition()
		   {
			   // this asumme RTTI works TODO need defines for Compliers

                std::string typeID = typeid(value).name(); 

				if(typeID == "int" || typeID == "long" )
				{
					   (typeID == "int" )? (CMLValuetypeName  = "IntegerValue", typeName = "AIntNumericBasicType") : (CMLValuetypeName  = "NaturalValue",typeName ="ANatOneNumericBasicType");
					   CMLtypeName = "longVal";
					   return;
				}
				else //----- add logic here
				{
					(typeID == "bool" )?(CMLValuetypeName = "BooleanValue",typeName = "ABooleanBasicType"): (CMLValuetypeName = "QuoteValue",typeName="AQuoteType");
					CMLtypeName = "value";
					return;
				}
				//TODO
				// try to see if is a class
				// not supported right now

		   }
	};
	
	// CoSim default typesystem class
	class ACoSimulationTypeSystem
	{
	  public:

		  typedef enum {AQuoteType,ABooleanBasicType,AIntNumericBasicType }CML_TYPE;

		  ACoSimulationTypeSystem():basicTypes()
		  {
			   regDefaultTypeSystem();
		  }

		  void configTypeSystem()
		  {
			   regModeltypes();
		  }

		  virtual ~ACoSimulationTypeSystem(){}
	      
		   template <typename T>
		  TypePackage<T> getBacisTypeObject ()
		  {
			  typename TypePackage<T> t;
			  typename BasicTypeMap::iterator type = basicTypes.find(t.typeName);		 
			  if(type != basicTypes.end())
				  return  boost::get<TypePackage<T>>((*type).second);

			  throw CoSimulationFrameworkTypeSytemExecption("Not a valided CoSim basic type");
		  }

		  virtual bool isLegalType(const std::string& tname)const
		  {
			  	BasicTypeMap::const_iterator type = basicTypes.find(tname);		 
			    return (type != basicTypes.end());

		  }


	  protected:
	      typedef boost::variant< TypePackage<int>,  TypePackage<std::string>,TypePackage<bool> > BasicTypeSystem; 
	      typedef std::map <std::string,BasicTypeSystem> BasicTypeMap;
	      typedef std::pair<std::string,BasicTypeSystem> TypeObject;
		  
		  BasicTypeMap basicTypes;

		  void regDefaultTypeSystem()
		  {
			  TypePackage<int> i(0);
			  basicTypes.insert(TypeObject(i.typeName,i));
			  TypePackage<bool> b(false);
			  basicTypes.insert(TypeObject(b.typeName,b));
			  TypePackage<std::string> s ("");
			  basicTypes.insert(TypeObject(s.typeName,s));

		  }

		  // might be impl by the user
		  virtual void regModeltypes();
		   
	};

	// template class for defining model specific types, use the base type system as super class
	template <typename D>
	class CoSimulationModelTypeSystem: public ACoSimulationTypeSystem
	{
	   public:
		   CoSimulationModelTypeSystem():ACoSimulationTypeSystem(){}

		  template <typename T>
		  TypePackage<T> getModelTypeObject (const std::string& typeName)
		  {
			  typename ModelTypeSystemMap::iterator type = modeltypeMap.find(typeName);		 
			  if(type != modeltypeMap.end())
				  return  boost::get<TypePackage<T>>((*type).second);

			  return ACoSimulationTypeSystem::getBacisTypeObject<T>(typeName);
			  
		  }

		   bool isLegalType(const std::string& tname)const
		  {
			  	typename ModelTypeSystemMap::const_iterator type = modeltypeMap.find(tname);		 
				return ((type != modeltypeMap.end())? true:  ACoSimulationTypeSystem::isLegalType(tname));
				
		  }
	   
	  protected:
		 typedef D ModelTypeSystem;
         typedef std::map <std::string,ModelTypeSystem> ModelTypeSystemMap;
	     typedef std::pair<std::string,ModelTypeSystem> ModelTypeObject;
		 ModelTypeSystemMap modeltypeMap;

	};

	// class for holder infomation regarding the operation types, used for semantics mapping in the CoSim framework
	class ChannelOperation
	{
	  public:
		  typedef enum {SYNC,SYNC_ON,READ,WRITE} ChannelOperationType;
		  friend std::ostream& operator<< (std::ostream& stream, const ChannelOperationType& obj)
		  {
			  switch (obj)
				{  case SYNC:
					  stream<<"SYNC";
					  break;
				  case SYNC_ON:
					  stream<<"SYNC_ON";
					  break;
				  case READ:
					  stream<<"READ";
					  break;
				  case WRITE:
					  stream<<"WRITE";
					  break;
				  default:
					  break;
			  }
			  return stream;
		  }
	};

	// data class for a CML action, contain the needed simulation infomation regarding operatins for a event option in the tool
	template <typename T>
	class ChannelAction
	{
	  public:
		  ChannelAction(const ChannelOperation::ChannelOperationType& t):opType(t){}
		  ChannelAction(const ChannelOperation::ChannelOperationType& t, const T& a):opType(t),type(a){}
		  
		  ChannelOperation::ChannelOperationType opType;
		  T type;
	};

	// class defining  CML event option, contains channel name, opreation type, data type and possiple a vaælue for the data type
	//imple the IchannelEventobject fram  the transport layer LIB
	template <typename V = void*>
	class ChannelEventObject:public CoSimulationTransportLayer::IChannelEventObject
	{
     
	 public:
		 ChannelEventObject(){}
		 ChannelEventObject(const std::string& v,const  ChannelOperation::ChannelOperationType& actionType):channelName(v),action(actionType, TypePackage<V>()){}
		 ChannelEventObject(const std::string& v,const  ChannelOperation::ChannelOperationType& actionType,const V& c):channelName(v),action(actionType, TypePackage<V>(c)){}
		 virtual ~ChannelEventObject(){}
	    
		 
		 std::string channelName ; 
		 ChannelAction< TypePackage<V> > action;

		 std::string getTypedefintionName()const
		 {
			 return  action.type.CMLtypeName;
		 }

		 std::string getChannelName()const
		 {
			 return channelName;
		 }
		 std::string getOperationType () const
		 {
			 std::ostringstream s ;
			 s<<action.opType;
			 return  s.str();
		 }

		 std::string getInterpreterValueTypeName () const
		 {
			 return action.type.CMLValuetypeName; 
		 }
		 std::string getTypeName () const
		{
			return action.type.typeName;
		 }

		 std::string getTypeValue ()const
         {
			 std::ostringstream s ;

			 if(action.type.CMLValuetypeName == "QuoteValue")
				 s<<"\""<<action.type()<<"\"";
			 else
			    s<<std::boolalpha<<action.type();

			 return  s.str();
		 }

		 std::string getTypeNameAndValue () const
		 {
			  std::ostringstream str;
			  str<<"\""<<action.type.getTypeName()<<"\""<<" : "<<std::boolalpha<<action.type();  
			  return str.str();
		 }

		void upDateTypeValue (const std::string& newValue)
		{
			if (action.opType ==  ChannelOperation::SYNC )
				throw 1;

             std::istringstream s (newValue) ;
			 s>>action.type.value;			 
		}

		  friend std::ostream& operator<< (std::ostream& stream, const ChannelEventObject<V>& obj)
		  {
			  stream<<obj.getChannelName()<<std::endl;
			  stream<<obj.getOperationType()<<std::endl;
			  stream<<obj.getTypeName()<<std::endl;
			  try{stream<<obj.getTypeValue()<<std::endl;}catch(...){}
			  return stream;
		  }
	};


	template <typename T = ACoSimulationTypeSystem>
	class ACoSimulationCallback: public CoSimulationTransportLayer::ICoSimulationCallback
	{
	  public:
		  ACoSimulationCallback():typesystem()
		  {
			  typesystem.configTypeSystem();
		  }

		  CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSet inspect(){return CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSet();}
		  void execute(CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSmartPtr evt){}
		  bool finished()const {return true;}
		  void init(){}
		  void deInit(){}

	protected:
		T typesystem;

		template <typename T>
		CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSmartPtr createReadEventObject(const std::string& v )
		{
			typename TypePackage<T> t;
			checkType(t.typeName);
			
			return CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSmartPtr ( new ChannelEventObject<T>(v, ChannelOperation::READ));
		}

		CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSmartPtr createSyncEventObject(const std::string& v)
		{
			return CoSimulationTransportLayer::IChannelEventObject:: ChannelEventObjectSmartPtr (new ChannelEventObject<>(v, ChannelOperation::SYNC));
		}

		template <typename V>
		CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSmartPtr createWriteSyncOnEventObject(const std::string& v,const  ChannelOperation::ChannelOperationType& actionType, const V& c)
		{
			typename TypePackage<V> t;
			checkType(t.typeName);
			return CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSmartPtr (new ChannelEventObject<V>(v,actionType,c));
	
		}

		bool checkType(const std::string& typeName)
		{
			 return ((typesystem.isLegalType(typeName))? true: throw CoSimulationFrameworkTypeSytemExecption("Not a valided CoSim basic type"));

		}
	};

	template <typename C, typename A = ACoSimulationTypeSystem, typename T = CoSimulationTransportLayer::DefaultCoSimulationTransportLayer>
	class ACoSimulationModel: public CoSimulationTransportLayer::IComErrorCallback 
	{
	 public:
		  typedef boost::mutex::scoped_lock ScopeLock;
		  typedef boost::shared_ptr<C> CoSimulationObject; 
		  typedef boost::shared_ptr<T> CoSimulationTransportLayerObject; 

		  ACoSimulationModel(const std::string modelName):typeFac(),transportObject(),frontendObject(),cmlProcessName(modelName),active(false),cond()
		  {	
			  typeFac.configTypeSystem();
			  transportObject.reset(new T);
			  frontendObject.reset(new C);
		  }

		  void initModel(const std::string& endPoint, const int& portNumber)
		  {
			 ScopeLock tmp (mut);
			  //section for sitting up the transport layer
			try
			{
			   transportObject->connect(endPoint,portNumber,cmlProcessName);
			   transportObject->setComErrorCallbackObject(this);
			}
			catch(...)
			{
			   frontendObject.reset();
			   transportObject.reset();
			   throw ; // re-throw
			}
			// if OK start the frontend component
			frontendObject->init();
			transportObject->setCallbackObject(frontendObject);
			
			active = true;
			transportObject->start(); //giving control to the transport thread. Possiple data race here

			 while(active)
				cond.wait(tmp);    // wait on the transport thread to finshed or get interupt by the user

			 closedownLogic(); // add errro handling here.... the control is now back to the calling thread

		  }

		  void TransportLayerStopped(const CoSimulationTransportLayer::IComErrorCallback::TRANSPORT_COM_ERROR&  evt = NONE_ERROR)
		  {
			  try{
			  	deInitModel();
			  }
			  catch(...){}
		  }

		  void deInitModel()
		  {
			  try   // where is a problem here, execptions will be throwed in another thread
			  {
			    ScopeLock l (mut);
				if(active)
				 {
					active = false;
                    cond.notify_one();
				 }
			  }
			  catch (...)
			  {
				  throw; // re-throw
			  }
		  }

		  ~ACoSimulationModel()
		  {
			  try
			  {
			    deInitModel();
			   }
			  catch(...){} // C++ deconstructors must never re-throw

		  }

		  inline std::string getCMLProcessname()const
		  {
			  return cmlProcessName;
		  }

	 private:
		  A typeFac;
		  CoSimulationObject frontendObject;
		  CoSimulationTransportLayerObject transportObject;
		  mutable boost::mutex mut;
		  std::string cmlProcessName;
		  volatile bool active;
		  boost::condition_variable cond;
		  

		  //caller of this function must hold the class mutex and handle exepction throws
		  void closedownLogic()
		  {

			if(transportObject.get())
			{
               transportObject->setComErrorCallbackObject(0);
			   transportObject->stop(); // take control back  todo join threads
               transportObject->disConnect();
			   transportObject.reset();
			}

			if(frontendObject.get())
			{
			  frontendObject->deInit();
			  frontendObject.reset();
			}
		  }

	};



}

#endif