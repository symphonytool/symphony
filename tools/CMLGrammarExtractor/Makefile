############################################################
## Makefile for the CML grammar extractor
## ---------------------------------------------------------
##
## Usage:
##   make [target]
##
## Targets:
##   cmlGrammarExtractor.byte     -- build an ocaml byte code cmlGrammarExtractor  (default target)
##   cmlGrammarExtractor.native   -- build an native code cmlGrammarExtractor 
##   cmlGrammarExtractor.top      -- build an interactive cmlGrammarExtractor toplevel
##   cmlGrammarExtractor.debug    -- build a cmlGrammarExtractor with debugging symbols
##   cmlGrammarExtractor.profile  -- build a cmlGrammarExtractor with profiling instrumentation
##   clean         -- clean up all build related files
##
############################################################

# (just ignore this thing...)
.PHONY: default all \
        cmlGrammarExtractor.byte cmlGrammarExtractor.native cmlGrammarExtractor.top cmlGrammarExtractor.debug cmlGrammarExtractor.profile \
        clean

# Default build target (defined below)
# should probably be either 'cmlGrammarExtractor.native' or 'cmlGrammarExtractor.byte'
cmlGrammarExtractor: cmlGrammarExtractor.byte

# Default target for Eclipse
all: cmlGrammarExtractor

# Directories
SRC_DIR := src
BLD_DIR := _build

# Base invokation of ocamlbuild
OCAMLBUILD := ocamlbuild -no-links -ocamlyacc "ocamlyacc -v" 

# Include, linking and library flags
LIBS   := str,unix
#,ptrees,zip,extLib,javalib
#FLAGS  := -I,$(PTREES),-I,$(JAVALIB),-I,$(CAMLZIP),-I,$(EXTLIB)
#CFLAGS := $(FLAGS)
#LFLAGS := $(FLAGS),-dllpath,$(PTREES),-dllpath,$(JAVALIB),-dllpath,$(CAMLZIP),-dllpath,$(EXTLIB)

cmlGrammarExtractor.byte: 
	@echo "*** Building cmlGrammarExtractor.byte"
#	@$(OCAMLBUILD) -cflags $(CFLAGS) -lflags $(LFLAGS) -libs $(LIBS) 
	@$(OCAMLBUILD) -libs $(LIBS) $(SRC_DIR)/main.byte 
	@ln -sf $(BLD_DIR)/$(SRC_DIR)/main.byte cmlGrammarExtractor.byte
	@ln -sf cmlGrammarExtractor.byte cmlGrammarExtractor

cmlGrammarExtractor.native: 
	@echo "*** Building cmlGrammarExtractor.native"
#	@$(OCAMLBUILD) -cflags $(CFLAGS) -lflags $(FLAGS) -libs $(LIBS) 
	@$(OCAMLBUILD) -libs $(LIBS) $(SRC_DIR)/main.native
	@ln -sf $(BLD_DIR)/$(SRC_DIR)/main.native cmlGrammarExtractor.native
	@ln -sf cmlGrammarExtractor.native cmlGrammarExtractor

cmlGrammarExtractor.debug: 
	@echo "*** Building cmlGrammarExtractor.debug"
#	@$(OCAMLBUILD) -cflags $(CFLAGS) -lflags $(LFLAGS) -libs $(LIBS) 
	@$(OCAMLBUILD) -libs $(LIBS) $(SRC_DIR)/main.d.byte
	@ln -sf _build/$(SRC_DIR)/main.d.byte cmlGrammarExtractor.debug

cmlGrammarExtractor.top: 
	@echo "*** Building cmlGrammarExtractor.top"
#	@echo $(MODS:%=$(SRC_DIR)/%) > $(SRC_DIR)/main.mltop
	@$(OCAMLBUILD) -libs $(LIBS) $(SRC_DIR)/main.top
	@rm -f $(SRC_DIR)/main.mltop
	@ln -sf _build/$(SRC_DIR)/main.top cmlGrammarExtractor.top

clean:
	$(OCAMLBUILD) -clean
	rm -f cmlGrammarExtractor cmlGrammarExtractor.byte cmlGrammarExtractor.native cmlGrammarExtractor.top cmlGrammarExtractor.debug cmlGrammarExtractor.profile
	rm -f $(SRC_DIR)/main.mltop
	rm -f .ocamlinit
	rm -f parser.output

## Some specially generated files
.ocamlinit:
	@echo "#directory \"$(BLD_DIR)/$(SRC_DIR)\";;" > .ocamlinit

parser.output:
	@ln -sf $(BLD_DIR)/$(SRC_DIR)/parser.output parser.output

