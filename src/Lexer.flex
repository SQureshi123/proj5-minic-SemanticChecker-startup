/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *    Group Members: Shiza Qureshi & Tyler Page                            *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%%

%class Lexer
%byaccj

%{

  public Parser   parser;
  public int      lineno;
  public int      column;

  public Lexer(java.io.Reader r, Parser parser) {
    this(r);
    this.parser = parser;
    this.lineno = 1;
    this.column = 1;
  }
   public int getLine(){
        return yyline;
    }

    public int getColumn(){
      return yycolumn-yylength();
    }


%}

num          = [0-9]+("."[0-9]+)?
identifier   = [a-zA-Z][a-zA-Z0-9_]*
newline      = \n
whitespace   = [ \t\r]+
linecomment  = "//".*
blockcomment = "{"[^]*"}"

%%

"func"                              { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.FUNC       ; }
"return"                            { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.RETURN     ; }
"var"                               { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.VAR        ; }
"if"                                { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.IF         ; }
"then"                              { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.THEN       ; }
"else"                              { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.ELSE       ; }
"begin"                             { parser.yylval = new ParserVal(new Token(yytext())); yycolumn = 1; yyline++; return Parser.BEGIN      ; }
"end"                               { parser.yylval = new ParserVal(new Token(yytext())); yycolumn = 1; yyline++; return Parser.END        ; }
"while"                             { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.WHILE      ; }
"("                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.LPAREN     ; }
")"                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.RPAREN     ; }
"["                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.LBRACKET   ; }
"]"                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.RBRACKET   ; }
"new"                               { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.NEW        ; }
"num"                               { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.NUM        ; }
"bool"                              { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.BOOL       ; }
"print"                             { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.PRINT      ; }
"."                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.DOT        ; }
"size"                              { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.SIZE       ; }
":="                                { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.ASSIGN     ; }
"::"                                { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.TYPEOF     ; }
"+"                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.ADD        ; }
"-"                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.SUB        ; }
"*"                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.MUL        ; }
"/"                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.DIV        ; }
"%"                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.MOD        ; }
"and"                               { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.AND        ; }
"or"                                { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.OR         ; }
"not"                               { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.NOT        ; }
"<"                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.LT         ; }
">"                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.GT         ; }
"<="                                { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.LE         ; }
">="                                { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.GE         ; }
"="                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.EQ         ; }
"<>"                                { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.NE         ; }
";"                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.SEMI       ; }
"true"|"false"                      { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.BOOL_LIT   ; }
","                                 { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.COMMA      ; }
{num}                               { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.NUM_LIT    ; }
{identifier}                        { parser.yylval = new ParserVal(new Token(yytext())); yycolumn+=yylength(); return Parser.IDENT      ; }
{linecomment}                       { parser.yylval = new ParserVal(new Token(yytext())); yycolumn=1; yyline++; }
{newline}                           { yyline++; yycolumn = 1; }
{whitespace}                        { yycolumn+=yylength(); }
{blockcomment}                      { parser.yylval = new ParserVal (new Token(yytext())); yycolumn = 1; yyline++; }


\b     { System.err.println("Sorry, backspace doesn't work"); }


/* error fallback */
[^]    { /* skip */ }
