%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% compassreport.cls
% Template for large COMPASS Report (e.g. deliverable, tech
% note). This style allows for the use of chapters as well
% as sections, subsections, etc.
% Richard Payne, 2011
% Adapted From DEPLOY Report Template, Thai Son Hoang, 2008
% Doc History page from DESTECS stylem, 2011
% 
% USAGE:
% \documentclass[a4paper, 12pt, twoside]{compassreportlarge}
% \reportnumber{DXX}
% \reporttitle{Report on xxxx}
% \status{Public Document}
% \submissiondate{31 January 2011}
% \contributors{
%    John Fitzgerald & NCL \\
%    Peter Gorm Larson & IHA
% }
% \editors{
%    John Fitzgerald & NCL \\
%    Peter Gorm Larson & IHA
% }
% \reviewers{John Fitzgerald & NCL \\
%    Peter Gorm Larson & IHA}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\NeedsTeXFormat{LaTeX2e}\relax
\ProvidesClass{compasslargereport}[2011/12/21 COMPASS Large Report Template]

%%Add [final] option to the report class to switch between draft and final version of the report
\newif\iffinalversion						%Define new if 
\finalversionfalse							%Default value is false 
\DeclareOption{final}{\finalversiontrue}	%Set to true if final option is given
% Add [lineno] option to the report class to add line numbers to each text line
\newif\iflineno								%Define new if
\linenotrue                                  %Default value is true
\DeclareOption{lineno}{\linenotrue}			%Set to true if option is given
% Add [narrowmargin] option to the report class to use narrow margins
\newif\ifnarrowmargin								%Define new if
\narrowmarginfalse                                 %Default value is true
\DeclareOption{narrowmargin}{\narrowmargintrue}			%Set to true if option is given


%%%% Load class article and redefine \maketitle after
%%%% declaring all the options 
\DeclareOption*{\PassOptionsToClass{\CurrentOption}{report}}
\ProcessOptions\relax
\LoadClass{report}


%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%% Requiring Packages%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%


\RequirePackage[T1]{fontenc}

%\RequirePackage{ifthen}

%%%% Require package for display URL; use {hyperref} instead of {url} %%%%
\RequirePackage{hyperref}

%%%% Require graphicx for various images %%%%
\RequirePackage{graphicx}

%%%% Require color %%%%
\RequirePackage[RGB]{xcolor}

%%%% Require fancy header %%%%
\RequirePackage{fancyhdr}

%%%% Require table colour package for version history table %%%%
\RequirePackage{colortbl}

\RequirePackage{tabularx}	% Adds possibility to maximize the width of columns (and enables word wrap)


\renewcommand{\sfdefault}{phv}


%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Conditional package includes dependent on the class options 
%%%%%%%%%%%%%%%%%%%%%%%%%%%
\iffinalversion
	% Process final version stuff
\else
	% Process options for the draft mode
	\iflineno
		\RequirePackage{lineno}	% Show line numbers for reviewing purposes (Ubuntu: texlive-humanities package)
		\linenumbers[1]
	\fi % lineno
\fi % finalversion
\ifnarrowmargin
	% Process narrow margins
\usepackage[left=1.0in, right=1.0in, top=1.0in, bottom=1.0in]{geometry}
\fi % narrowmargins




%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%% Defining variables%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%% Various variables %%%% 
\newcommand{\compass@reportnumber}{Document Number}
\newcommand{\reportnumber}[1]{\renewcommand{\compass@reportnumber}{#1}}
\newcommand{\compass@reporttitle}{Document Title}
\newcommand{\reporttitle}[1]{\renewcommand{\compass@reporttitle}{#1}}
\newcommand{\compass@shortreporttitle}{Short Document Title}
\newcommand{\shortreporttitle}[1]{\renewcommand{\compass@shortreporttitle}{#1}}
\newcommand{\compass@reportstatus}{Document Status}
\newcommand{\reportstatus}[1]{\renewcommand{\compass@reportstatus}{#1}}
\newcommand{\compass@submissiondate}{Submission date} 
\newcommand{\submissiondate}[1]{\renewcommand{\compass@submissiondate}{#1}}

%%%%% Optional variables %%%%%
\newcommand{\compass@editors}{Document Editors}
\newcommand{\editors}[1]{\renewcommand{\compass@editors}{#1}}
\newcommand{\compass@contributors}{Document Contributors}
\newcommand{\contributors}[1]{\renewcommand{\compass@contributors}{#1}}
\newcommand{\compass@reviewers}{Document Reviewers}
\newcommand{\reviewers}[1]{\renewcommand{\compass@reviewers}{#1}}

%%%% Determine whether deliverable or technote %%%%
%%%% (from KGP Destecs addition, 2010/10/06) %%%%
\newif\iftechnote
% default to original behaviour
\technotefalse
\newcommand{\doctype}{\iftechnote{Technical Note}\else{Deliverable}\fi}


%%%%%%%%%%%%%%%%%%%%%%
%%%% Style things %%%%
%%%%%%%%%%%%%%%%%%%%%%

%%%% Make paragraphs start on new line, with no indentation %%%%
\RequirePackage[parfill]{parskip}

%%%% Set depth of table of contents %%%%
\setcounter{tocdepth}{2}


%%%% Set change log details %%%%
\def\@changelog{\relax}
% #1: version
% #2: date
% #3: author
% #4: description
\def\addversion#1#2#3#4{
\expandafter\def\expandafter\@changelog\expandafter{\@changelog
#1 & #2 & #3 & #4 \\\hline
}
\def\compass@docversion{#1}
}


%%%%%%%%%%%%%%%%%%%%%%%%%
%%%% Defining Header %%%%
%%%%%%%%%%%%%%%%%%%%%%%%%
 
 \pagestyle{fancy}
 %Left side of header - include doc number, short title and report status
 \lhead{\sffamily \compass@reportnumber\ -\ \compass@shortreporttitle\ (\compass@reportstatus)}
 \rhead{\includegraphics[height=0.4cm]{include/compass-logo}} %Compass logo
 \renewcommand{\headrulewidth}{0.5pt} %solid line

 \clearpage


%%%%%%%%%%%%%%%%%%%%%%%
%%%% Document Info %%%%
%%%%%%%%%%%%%%%%%%%%%%%

%% Change 'titlepage' into a page containing the document information
\def\maketitle{
\createdocinfo
}

\def\createdocinfo{
 %%%% Title page%%%%
 \thispagestyle{empty}
 \begin{center}
   \sffamily
   \includegraphics[width=0.2\textwidth]{include/fp7-logo}\\
  %   \vfill
   %{\large Project: COMPASS}\\[2ex]
   {\large Grant Agreement: 287829}\\[2ex]
   {\large Comprehensive Modelling for Advanced Systems of Systems}\\
     \vfill
   \includegraphics[width=1\textwidth]{include/compass-logo}
     \vfill
   {\textbf{\Large \compass@reporttitle}}\\[4ex]
   {\large \doctype\ Number: \compass@reportnumber}\\[2ex]
   {\large Version: \compass@docversion}\\[2ex]
   {\large Date: \compass@submissiondate}\\[4ex]
   {\large \compass@reportstatus\ Document}
     \vfill
   {http://www.compass-research.eu}
 \end{center}
 \clearpage
 
 
 %%%% Contributors etc 
 \ifx\compass@contributors\@empty
 \else
   \textbf{\large Contributors:}

   \bigskip
     \compass@contributors
 \fi

 \ifx\compass@editors\@empty
 \else
 \bigskip
 \bigskip
   \textbf{\large Editors:}

   \bigskip
     \compass@editors
 \fi

 \ifx\compass@reviewers\@empty
 \else
 \bigskip
   \textbf{\large Reviewers:}

   \bigskip
     \compass@reviewers
 \fi
 \clearpage


%%%% Document history page %%%% 
 \vskip0.5cm
 \if\@changelog\relax\else

 \section*{Document History}
 \begin{tabularx}{\linewidth}[h!]{| l | l | l | X |}
 \rowcolor[gray]{.8}\hline
 \textbf{Ver} & \textbf{Date} & \textbf{Author} & \textbf{Description} \\\hline
 \@changelog
 \end{tabularx}
 \vskip0.5cm
 \clearpage
 \fi
}
