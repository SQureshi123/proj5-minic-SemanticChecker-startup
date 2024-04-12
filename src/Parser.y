/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2001 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * This is a modified version of the example from                          *
 *   http://www.lincom-asg.com/~rjamison/byacc/                            *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%{
import java.io.*;
%}

%right  ASSIGN
%left   OR
%left   AND
%right  NOT
%left   EQ      NE
%left   LE      LT      GE      GT
%left   ADD     SUB
%left   MUL     DIV     MOD

%token <obj>    EQ   NE   LE   LT   GE   GT
%token <obj>    ADD  SUB  MUL  DIV  MOD
%token <obj>    OR   AND  NOT

%token <obj>    IDENT     NUM_LIT   BOOL_LIT

%token <obj> BOOL  NUM  TYPEOF
%token <obj> FUNC  IF  THEN  ELSE  WHILE  PRINT  RETURN
%token <obj> BEGIN  END  LPAREN  RPAREN  LBRACKET  RBRACKET
%token <obj> ASSIGN  VAR  SEMI  COMMA  NEW  DOT  SIZE

%type <obj> program   decl_list  decl
%type <obj> func_decl  local_decls  local_decl  type_spec  prim_type
%type <obj> params  param_list  param  args  arg_list
%type <obj> stmt_list  stmt  assign_stmt  print_stmt  return_stmt  if_stmt  while_stmt  compound_stmt     
%type <obj> expr

%%


program         : decl_list                                     { Debug("program -> decl_list"                  ); $$ = program____decllist($1); }
                ;

decl_list       : decl_list decl                                { Debug("decl_list -> decl_list decl"           ); $$ = decllist____decllist_decl($1,$2); }
                |                                               { Debug("decl_list -> eps"                      ); $$ = decllist____eps          (     ); }
                ;

decl            : func_decl                                     { Debug("decl -> func_decl"                     ); $$ = decl____funcdecl($1); }
                ;

prim_type       : NUM                                           { Debug("prim_type -> num"                      ); $$ = primtype____NUM($1); }
                ;

type_spec       : prim_type                                     { Debug("type_spec -> prim_type"                ); $$ = typespec____primtype($1); }
                ;

func_decl       : FUNC IDENT TYPEOF type_spec LPAREN params RPAREN BEGIN local_decls{ Debug("func_decl -> func ID::type_spec(params) begin local_decls"); $<obj>$ = fundecl____FUNC_IDENT_TYPEOF_typespec_LPAREN_params_RPAREN_BEGIN_localdecls_10X_stmtlist_END($1,$2,$3,$4,$5,$6,$7,$8,$9            ); }
                                                                       stmt_list END{ Debug("                                            stmt_list end"); $$ =      fundecl____FUNC_IDENT_TYPEOF_typespec_LPAREN_params_RPAREN_BEGIN_localdecls_X10_stmtlist_END($1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12); }
                ;

params          :                                               { Debug("params -> eps"                         ); $$ = params____eps(); }
                ;

param_list      : param_list COMMA param                        {  }
                | param                                         {  }
                ;

param           : IDENT TYPEOF type_spec                        {  }
                ;

stmt_list       : stmt_list stmt                                { Debug("stmt_list -> stmt_list stmt"           ); $$ = stmtlist____stmtlist_stmt($1,$2); }
                |                                               { Debug("stmt_list -> eps"                      ); $$ = stmtlist____eps          (     ); }
                ;

stmt            : assign_stmt                                   { Debug("stmt -> assign_stmt"                   ); $$ = stmt____assignstmt  ($1); }
                | return_stmt                                   { Debug("stmt -> return_stmt"                   ); $$ = stmt____returnstmt  ($1); }
                ;

assign_stmt     : IDENT ASSIGN expr SEMI                        { Debug("assign_stmt -> IDENT := expr ;"        ); $$ = assignstmt____IDENT_ASSIGN_expr_SEMI($1,$2,$3,$4); }
                ;

print_stmt      : PRINT expr SEMI                               { $$ = print_stmt____PRINT_expr_SEMI($1, $2, $3); }
                ;

return_stmt     : RETURN expr SEMI                              { Debug("return_stmt -> return expr ;"          ); $$ = returnstmt____RETURN_expr_SEMI($1,$2,$3); }
                ;

if_stmt         : IF expr THEN stmt_list ELSE stmt_list END     {  }
                ;

while_stmt      : WHILE expr BEGIN stmt_list END                {  }
                ;

compound_stmt   : BEGIN local_decls stmt_list END               {  }
                ;

local_decls     : local_decls  local_decl                       { Debug("local_decls -> local_decls local_decl" ); $$ = localdecls____localdecls_localdecl($1,$2); }
                |                                               { Debug("local_decls -> eps"                    ); $$ = localdecls____eps(); }
                ;

local_decl      : VAR  IDENT  TYPEOF  type_spec  SEMI           { Debug("local_decl -> var IDENT :: type_spec ;"); $$ = localdecl____VAR_IDENT_TYPEOF_typespec_SEMI($1,$2,$3,$4,$5); }
                ;

args            :                                               { Debug("args -> eps"                           ); $$ = args____eps(); }
                ;

arg_list        : arg_list COMMA expr                           {  }
                | expr                                          {  }
                ;

expr            : expr ADD expr                                 { Debug("expr -> expr ADD expr"                 ); $$ = expr____expr_ADD_expr           ($1,$2,$3   ); }
                | expr EQ  expr                                 { Debug("expr -> expr EQ  expr"                 ); $$ = expr____expr_EQ_expr            ($1,$2,$3   ); }
                | LPAREN expr RPAREN                            { Debug("expr -> LPAREN expr RPAREN"            ); $$ = expr____LPAREN_expr_RPAREN      ($1,$2,$3   ); }
                | IDENT                                         { Debug("expr -> IDENT"                         ); $$ = expr____IDENT                   ($1         ); }
                | NUM_LIT                                       { Debug("expr -> NUM_LIT"                       ); $$ = expr____NUMLIT                  ($1         ); }
                | IDENT LPAREN args RPAREN                      { Debug("expr -> IDENT LPAREN args RPAREN"      ); $$ = expr____IDENT_LPAREN_args_RPAREN($1,$2,$3,$4); }
                ;

%%
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
        //System.out.println ("Error message for " + lexer.lineno+":"+lexer.column +" by Parser.yyerror(): " + error);
        int last_token_lineno = 0;
        int last_token_column = 0;
        System.out.println ("Error message by Parser.yyerror() at near " + last_token_lineno+":"+last_token_column + ": " + error);
    }


    public Parser(Reader r, boolean yydebug) {
        this.lexer   = new Lexer(r, this);
        this.yydebug = yydebug;
    }