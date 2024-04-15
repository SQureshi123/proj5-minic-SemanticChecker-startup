//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 15 "Parser.y"
import java.io.*;
//#line 19 "Parser.java"




public class Parser
             extends ParserImpl
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ASSIGN=257;
public final static short OR=258;
public final static short AND=259;
public final static short NOT=260;
public final static short EQ=261;
public final static short NE=262;
public final static short LE=263;
public final static short LT=264;
public final static short GE=265;
public final static short GT=266;
public final static short ADD=267;
public final static short SUB=268;
public final static short MUL=269;
public final static short DIV=270;
public final static short MOD=271;
public final static short IDENT=272;
public final static short NUM_LIT=273;
public final static short BOOL_LIT=274;
public final static short BOOL=275;
public final static short NUM=276;
public final static short TYPEOF=277;
public final static short FUNC=278;
public final static short IF=279;
public final static short THEN=280;
public final static short ELSE=281;
public final static short WHILE=282;
public final static short PRINT=283;
public final static short RETURN=284;
public final static short BEGIN=285;
public final static short END=286;
public final static short LPAREN=287;
public final static short RPAREN=288;
public final static short LBRACKET=289;
public final static short RBRACKET=290;
public final static short VAR=291;
public final static short SEMI=292;
public final static short COMMA=293;
public final static short NEW=294;
public final static short DOT=295;
public final static short SIZE=296;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    7,    7,    6,    6,   22,    3,
    8,    8,    9,    9,   10,   13,   13,   14,   14,   15,
   15,   16,   17,   18,   19,   20,    4,    4,    5,   11,
   12,   12,   21,   21,   21,   21,   21,   21,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    1,    1,    1,    3,    0,   12,
    1,    0,    3,    1,    3,    2,    0,    1,    1,    4,
    7,    3,    3,    7,    5,    4,    2,    0,    5,    0,
    3,    1,    3,    3,    3,    1,    1,    4,
};
final static short yydefred[] = {                         3,
    0,    0,    0,    2,    4,    0,    0,    6,    5,    0,
    0,    0,    0,    0,    0,    0,   14,    8,    0,    0,
    0,   15,   28,   13,    0,    0,   27,   17,    0,    0,
    0,    0,    0,   10,   16,   18,   19,    0,    0,    0,
    0,   37,    0,    0,   29,    0,    0,   30,    0,    0,
    0,   23,   20,    0,    0,   35,    0,   33,    0,   38,
    0,   21,
};
final static short yydgoto[] = {                          1,
    2,    4,    5,   25,   27,   10,   11,   15,   16,   17,
   55,    0,   30,   35,   36,    0,   37,    0,    0,    0,
   44,   28,
};
final static short yysindex[] = {                         0,
    0, -277, -267,    0,    0, -266, -221,    0,    0, -265,
 -268, -247, -270, -253, -261, -263,    0,    0, -221, -242,
 -247,    0,    0,    0, -243, -222,    0,    0, -225, -258,
 -221, -257, -269,    0,    0,    0,    0, -232, -269, -269,
 -226,    0, -269, -255,    0, -252, -248,    0, -244, -269,
 -269,    0,    0, -195, -220,    0, -201,    0, -269,    0,
 -251,    0,
};
final static short yyrindex[] = {                         0,
    0,   67,    0,    0,    0,    0,    0,    0,    0,    0,
 -229, -219,    0,    0,    0, -218,    0,    0,    0,    0,
    0,    0,    0,    0, -233,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -259,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -254,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,   16,    0,    0,    0,   50,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    6,    0,
};
final static int YYTABLESIZE=71;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         39,
    3,   36,   41,   42,    6,   50,   34,   36,   50,   50,
    7,   51,   50,   32,   51,   51,   50,   43,   51,   18,
   13,   12,   51,   19,   14,   33,   20,   34,   36,   21,
   36,   40,   36,   34,   22,   34,   52,   34,    9,   53,
   62,   54,   23,   56,   46,   47,   38,   26,   49,   29,
    9,   31,    9,    8,    9,   57,   58,    7,    7,   45,
   48,   59,    7,    7,   61,   51,    1,   60,   12,   11,
   24,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        257,
  278,  261,  272,  273,  272,  261,  261,  267,  261,  261,
  277,  267,  261,  272,  267,  267,  261,  287,  267,  290,
  289,  287,  267,  277,  272,  284,  288,  286,  288,  293,
  290,  289,  292,  288,   19,  290,  292,  292,  272,  292,
  292,  290,  285,  288,   39,   40,   31,  291,   43,  272,
  284,  277,  286,  275,  276,   50,   51,  287,  288,  292,
  287,  257,  292,  293,   59,  267,    0,  288,  288,  288,
   21,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=296;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"ASSIGN","OR","AND","NOT","EQ","NE","LE","LT","GE","GT","ADD",
"SUB","MUL","DIV","MOD","IDENT","NUM_LIT","BOOL_LIT","BOOL","NUM","TYPEOF",
"FUNC","IF","THEN","ELSE","WHILE","PRINT","RETURN","BEGIN","END","LPAREN",
"RPAREN","LBRACKET","RBRACKET","VAR","SEMI","COMMA","NEW","DOT","SIZE",
};
final static String yyrule[] = {
"$accept : program",
"program : decl_list",
"decl_list : decl_list decl",
"decl_list :",
"decl : func_decl",
"prim_type : NUM",
"prim_type : BOOL",
"type_spec : prim_type",
"type_spec : prim_type LBRACKET RBRACKET",
"$$1 :",
"func_decl : FUNC IDENT TYPEOF type_spec LPAREN params RPAREN BEGIN local_decls $$1 stmt_list END",
"params : param_list",
"params :",
"param_list : param_list COMMA param",
"param_list : param",
"param : IDENT TYPEOF type_spec",
"stmt_list : stmt_list stmt",
"stmt_list :",
"stmt : assign_stmt",
"stmt : return_stmt",
"assign_stmt : IDENT ASSIGN expr SEMI",
"assign_stmt : IDENT LBRACKET expr RBRACKET ASSIGN expr SEMI",
"print_stmt : PRINT expr SEMI",
"return_stmt : RETURN expr SEMI",
"if_stmt : IF expr THEN stmt_list ELSE stmt_list END",
"while_stmt : WHILE expr BEGIN stmt_list END",
"compound_stmt : BEGIN local_decls stmt_list END",
"local_decls : local_decls local_decl",
"local_decls :",
"local_decl : VAR IDENT TYPEOF type_spec SEMI",
"args :",
"arg_list : arg_list COMMA expr",
"arg_list : expr",
"expr : expr ADD expr",
"expr : expr EQ expr",
"expr : LPAREN expr RPAREN",
"expr : IDENT",
"expr : NUM_LIT",
"expr : IDENT LPAREN args RPAREN",
};

//#line 130 "Parser.y"
    private Lexer lexer;
    private Token last_token;

    private int yylex () {
        int yyl_return = -1;
        try {
            yylval = new ParserVal(0);
            yyl_return = lexer.yylex();
            last_token = (Token)yylval.obj;
        }
        catch (IOException e) {
            System.out.println("IO error :"+e);
        }
        return yyl_return;
    }


    public void yyerror (String error) {

        System.out.println ("Error message for " + lexer.lineno+":"+lexer.column +" by Parser.yyerror(): " + error);
        int last_token_lineno = 0;
        int last_token_column = 0;
        System.out.println ("Error message by Parser.yyerror() at near " + last_token_lineno+":"+last_token_column + ": " + error);
    }


    public Parser(Reader r, boolean yydebug) {
        this.lexer   = new Lexer(r, this);
        this.yydebug = yydebug;
    }
//#line 304 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
throws Exception
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 47 "Parser.y"
{ Debug("program -> decl_list"                  ); yyval.obj = program____decllist(val_peek(0).obj); }
break;
case 2:
//#line 50 "Parser.y"
{ Debug("decl_list -> decl_list decl"           ); yyval.obj = decllist____decllist_decl(val_peek(1).obj,val_peek(0).obj); }
break;
case 3:
//#line 51 "Parser.y"
{ Debug("decl_list -> eps"                      ); yyval.obj = decllist____eps          (     ); }
break;
case 4:
//#line 54 "Parser.y"
{ Debug("decl -> func_decl"                     ); yyval.obj = decl____funcdecl(val_peek(0).obj); }
break;
case 5:
//#line 57 "Parser.y"
{ Debug("prim_type -> num"                      ); yyval.obj = primtype____NUM(val_peek(0).obj); }
break;
case 6:
//#line 58 "Parser.y"
{ Debug("prim_type -> bool"                     ); yyval.obj = primtype____BOOL(val_peek(0).obj); }
break;
case 7:
//#line 61 "Parser.y"
{ Debug("type_spec -> prim_type"                ); yyval.obj = typespec____primtype(val_peek(0).obj); }
break;
case 8:
//#line 62 "Parser.y"
{ Debug("type_spec -> prim_type LBRACKET RBRACKET"); yyval.obj= typespec____primtype_LBRACKET_RBRACKET(val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 9:
//#line 65 "Parser.y"
{ Debug("func_decl -> func ID::type_spec(params) begin local_decls"); yyval.obj = fundecl____FUNC_IDENT_TYPEOF_typespec_LPAREN_params_RPAREN_BEGIN_localdecls_10X_stmtlist_END(val_peek(8).obj,val_peek(7).obj,val_peek(6).obj,val_peek(5).obj,val_peek(4).obj,val_peek(3).obj,val_peek(2).obj,val_peek(1).obj,val_peek(0).obj            ); }
break;
case 10:
//#line 66 "Parser.y"
{ Debug("                                            stmt_list end"); yyval.obj =      fundecl____FUNC_IDENT_TYPEOF_typespec_LPAREN_params_RPAREN_BEGIN_localdecls_X10_stmtlist_END(val_peek(11).obj,val_peek(10).obj,val_peek(9).obj,val_peek(8).obj,val_peek(7).obj,val_peek(6).obj,val_peek(5).obj,val_peek(4).obj,val_peek(3).obj,val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 11:
//#line 69 "Parser.y"
{ Debug ("params -> param_list"                 ); yyval.obj = params____paramlist(val_peek(0).obj);  }
break;
case 12:
//#line 70 "Parser.y"
{ Debug("params -> eps"                         ); yyval.obj = params____eps(); }
break;
case 13:
//#line 73 "Parser.y"
{Debug("param_list -> param_list COMMA param"   ); yyval.obj = paramlist____paramlist_COMMA_param(val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 14:
//#line 74 "Parser.y"
{Debug("param_list -> param"                    ); yyval.obj = paramlist____param(val_peek(0).obj); }
break;
case 15:
//#line 77 "Parser.y"
{ Debug("param -> IDENT TYPEOF type_spec"       ); yyval.obj = param____IDENT_TYPEOF_typespec(val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 16:
//#line 80 "Parser.y"
{ Debug("stmt_list -> stmt_list stmt"           ); yyval.obj = stmtlist____stmtlist_stmt(val_peek(1).obj,val_peek(0).obj); }
break;
case 17:
//#line 81 "Parser.y"
{ Debug("stmt_list -> eps"                      ); yyval.obj = stmtlist____eps          (     ); }
break;
case 18:
//#line 84 "Parser.y"
{ Debug("stmt -> assign_stmt"                   ); yyval.obj = stmt____assignstmt  (val_peek(0).obj); }
break;
case 19:
//#line 85 "Parser.y"
{ Debug("stmt -> return_stmt"                   ); yyval.obj = stmt____returnstmt  (val_peek(0).obj); }
break;
case 20:
//#line 88 "Parser.y"
{ Debug("assign_stmt -> IDENT := expr ;"        ); yyval.obj = assignstmt____IDENT_ASSIGN_expr_SEMI(val_peek(3).obj,val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 21:
//#line 89 "Parser.y"
{ Debug("assign_stmt -> IDENT := expr ;"        ); yyval.obj = assignstmt____IDENT_LBRACKET_expr_RBRACKET_ASSIGN_expr_SEMI(val_peek(6).obj,val_peek(5).obj,val_peek(4).obj,val_peek(3).obj,val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 22:
//#line 92 "Parser.y"
{ Debug("print_stmt -> PRINT expr SEMI"); yyval.obj = print_stmt____PRINT_expr_SEMI(val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 23:
//#line 95 "Parser.y"
{ Debug("return_stmt -> return expr ;"          ); yyval.obj = returnstmt____RETURN_expr_SEMI(val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 24:
//#line 98 "Parser.y"
{ Debug("if_stmt -> IF expr THEN stmt_list ELSE stmt_list END"); yyval.obj = if_stmt____IF_expr_THEN_stmt_list_ELSE_stmt_list_END(val_peek(6).obj,val_peek(5).obj,val_peek(4).obj,val_peek(3).obj,val_peek(2).obj); }
break;
case 25:
//#line 101 "Parser.y"
{ Debug("while_stmt -> WHILE expr BEGIN stmt_list END"); yyval.obj = while_stmt____WHILE_expr_BEGIN_stmt_list_END(val_peek(4).obj,val_peek(3).obj,val_peek(2).obj,val_peek(1).obj); }
break;
case 26:
//#line 104 "Parser.y"
{ Debug("compound_stmt -> BEGIN local_decls stmt_list END"); yyval.obj = compound_stmt____BEGIN_local_decls_stmt_list_END(val_peek(3).obj,val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 27:
//#line 107 "Parser.y"
{ Debug("local_decls -> local_decls local_decl" ); yyval.obj = localdecls____localdecls_localdecl(val_peek(1).obj,val_peek(0).obj); }
break;
case 28:
//#line 108 "Parser.y"
{ Debug("local_decls -> eps"                    ); yyval.obj = localdecls____eps(); }
break;
case 29:
//#line 111 "Parser.y"
{ Debug("local_decl -> var IDENT :: type_spec ;"); yyval.obj = localdecl____VAR_IDENT_TYPEOF_typespec_SEMI(val_peek(4).obj,val_peek(3).obj,val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
case 30:
//#line 114 "Parser.y"
{ Debug("args -> eps"                           ); yyval.obj = args____eps(); }
break;
case 31:
//#line 117 "Parser.y"
{ Debug("arg_list -> arg_list COMMA expr");        yyval.obj = arg_list____arg_list_COMMA_expr (val_peek(2).obj, val_peek(1).obj, val_peek(0).obj); }
break;
case 32:
//#line 118 "Parser.y"
{ Debug("arg_list -> expr");                       yyval.obj = arg_list____expr(val_peek(0).obj); }
break;
case 33:
//#line 121 "Parser.y"
{ Debug("expr -> expr ADD expr"                 ); yyval.obj = expr____expr_ADD_expr           (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj   ); }
break;
case 34:
//#line 122 "Parser.y"
{ Debug("expr -> expr EQ  expr"                 ); yyval.obj = expr____expr_EQ_expr            (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj   ); }
break;
case 35:
//#line 123 "Parser.y"
{ Debug("expr -> LPAREN expr RPAREN"            ); yyval.obj = expr____LPAREN_expr_RPAREN      (val_peek(2).obj,val_peek(1).obj,val_peek(0).obj   ); }
break;
case 36:
//#line 124 "Parser.y"
{ Debug("expr -> IDENT"                         ); yyval.obj = expr____IDENT                   (val_peek(0).obj         ); }
break;
case 37:
//#line 125 "Parser.y"
{ Debug("expr -> NUM_LIT"                       ); yyval.obj = expr____NUMLIT                  (val_peek(0).obj         ); }
break;
case 38:
//#line 126 "Parser.y"
{ Debug("expr -> IDENT LPAREN args RPAREN"      ); yyval.obj = expr____IDENT_LPAREN_args_RPAREN(val_peek(3).obj,val_peek(2).obj,val_peek(1).obj,val_peek(0).obj); }
break;
//#line 605 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
