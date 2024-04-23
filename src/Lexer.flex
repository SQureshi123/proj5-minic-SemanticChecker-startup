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
        return yyline+1;
    }

    public int getColumn(){
      return yycolumn+1;
    }


%}

num          = [0-9]+("."[0-9]+)?
identifier   = [a-zA-Z][a-zA-Z0-9_]*
newline      = \n
whitespace   = [ \t\r]+
linecomment  = "//".*
blockcomment = "{"[^]*"}"

%%

"func"                              { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.FUNC       ; }
"return"                            { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.RETURN     ; }
"var"                               { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.VAR        ; }
"if"                                { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.IF         ; }
"then"                              { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.THEN       ; }
"else"                              { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.ELSE       ; }
"begin"                             { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.BEGIN      ; }
"end"                               { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.END        ; }
"while"                             { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.WHILE      ; }
"("                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.LPAREN     ; }
")"                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.RPAREN     ; }
"["                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.LBRACKET   ; }
"]"                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.RBRACKET   ; }
"new"                               { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.NEW        ; }
"num"                               { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.NUM        ; }
"bool"                              { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.BOOL       ; }
"print"                             { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.PRINT      ; }
"."                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.DOT        ; }
"size"                              { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.SIZE       ; }
":="                                { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.ASSIGN     ; }
"::"                                { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.TYPEOF     ; }
"+"                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.ADD        ; }
"-"                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.SUB        ; }
"*"                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.MUL        ; }
"/"                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.DIV        ; }
"%"                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.MOD        ; }
"and"                               { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.AND        ; }
"or"                                { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.OR         ; }
"not"                               { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.NOT        ; }
"<"                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.LT         ; }
">"                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.GT         ; }
"<="                                { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.LE         ; }
">="                                { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.GE         ; }
"="                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.EQ         ; }
"<>"                                { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.NE         ; }
";"                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.SEMI       ; }
"true"|"false"                      { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.BOOL_LIT   ; }
","                                 { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.COMMA      ; }
{num}                               { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.NUM_LIT    ; }
{identifier}                        { parser.yylval = new ParserVal(new Token(yytext())); column += yytext().length(); return Parser.IDENT      ; }
{linecomment}                       { /* skip */ }
{newline}                           {  lineno++; column = 0; }
{whitespace}                        { column += yytext().length(); }
{blockcomment}                      { /* skip */ }


\b     { System.err.println("Sorry, backspace doesn't work"); }


/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
