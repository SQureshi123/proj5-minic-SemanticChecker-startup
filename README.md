
Group Members: Shiza Qureshi & Tyler Page

1. goto "src" directory

2. copy jflex-1.6.1.jar into src directory

3. compile Lexer.jflex as follows:

java -jar jflex-1.6.1.jar Lexer.flex

4. copy yacc.exe into src directory
5.  compile Parser.y as follows

    .\yacc.exe -Jthrows="Exception" -Jextends=ParserImpl -Jnorun -J Parser.y
6. compile all java files

5. run program and capture its output as follows:

java Program fail_01a.min  > output_fail_01a.txt
