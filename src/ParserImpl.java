import java.util.*;
import java.util.HashMap;
//Group Members: Shiza Qureshi & Tyler Page
@SuppressWarnings("unchecked")
public class ParserImpl
{
    public static Boolean _debug = true;
    void Debug(String message)
    {
        if(_debug)
            System.out.println(message);
    }


    // This is for chained symbol table.
    // This includes the global scope only at this moment.
    Env env = new Env(null);
    // this stores the root of parse tree, which will be used to print parse tree and run the parse tree
    ParseTree.Program parsetree_program = null;

    public void enterBlock() {
        Env newEnv = new Env(env);
        env = newEnv;
    }

    public void exitBlock() {
        if (env.prev != null) {
            env = env.prev;
        } else {
        }
    }

    public static String ordinalSuffix(int value) {
        int hundredRemainder = value % 100;
        int tenRemainder = value % 10;

        if (hundredRemainder - tenRemainder == 10) {
            return "th";
        }

        switch (tenRemainder) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    private void setTypeBasedOnVal(ParseTree.Expr expr) {
        if (expr instanceof ParseTree.ExprIdent) {
            ParseTree.ExprIdent exprIdent = (ParseTree.ExprIdent) expr;

            Object value = env.Get(exprIdent.ident);

            try {
                Double.parseDouble(value.toString());
                expr.info.setValue(env.Get(exprIdent.ident).toString());
                expr.info.setType("num");
                if (value.toString().equals("num")) {
                    expr.info.setType("num");
                }
                return;
            } catch (NumberFormatException e) {
                boolean boolValue = Boolean.parseBoolean(value.toString());
                if (value.toString().equals("true") || value.toString().equals("false")) {
                    expr.info.setValue(env.Get(exprIdent.ident).toString());
                    expr.info.setType("bool");
                } else {
                    expr.info.setType("function");
                }
            }
        }
    }

    Object program____decllist(Object s1) throws Exception
    {
        // 1. check if decllist has main function having no parameters and returns int type
        // 2. assign the root, whose type is ParseTree.Program, to parsetree_program
        ArrayList<ParseTree.FuncDecl> decllist = (ArrayList<ParseTree.FuncDecl>)s1;
        parsetree_program = new ParseTree.Program(decllist);
        return parsetree_program;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object decllist____decllist_decl(Object s1, Object s2) throws Exception
    {
        ArrayList<ParseTree.FuncDecl> decllist = (ArrayList<ParseTree.FuncDecl>)s1;
        ParseTree.FuncDecl                decl = (ParseTree.FuncDecl           )s2;
        decllist.add(decl);
        return decllist;
    }
    Object decllist____eps() throws Exception
    {
        return new ArrayList<ParseTree.FuncDecl>();
    }
    Object decl____funcdecl(Object s1) throws Exception
    {
        ParseTree.FuncDecl funcdecl = (ParseTree.FuncDecl)s1;
        return funcdecl;
    }
    Object primtype____NUM(Object s1) throws Exception
    {
        ParseTree.TypeSpec typespec = new ParseTree.TypeSpec("num");
        return typespec;
    }
    Object primtype____BOOL(Object s1) throws Exception
    {
        ParseTree.TypeSpec typespec = new ParseTree.TypeSpec("bool");
        return typespec;
    }
    Object typespec____primtype(Object s1)
    {
        ParseTree.TypeSpec primtype = (ParseTree.TypeSpec)s1;
        return primtype;
    }
    Object typespec____primtype_LBRACKET_RBRACKET(Object s1, Object s2, Object s3) //NOT DONE
    {
        ParseTree.TypeSpec primtype = (ParseTree.TypeSpec)s1;
        ParseTree.TypeSpec arrayType = new ParseTree.TypeSpec(primtype.typename + "[]");
        return arrayType;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object fundecl____FUNC_IDENT_TYPEOF_typespec_LPAREN_params_RPAREN_BEGIN_localdecls_10X_stmtlist_END(Object s1, Object s2, Object s3, Object s4, Object s5, Object s6, Object s7, Object s8, Object s9) throws Exception
    {
        // 1. add function_type_info object (name, return type, params) into the global scope of env
        // 2. create a new symbol table on top of env
        // 3. add parameters into top-local scope of env
        // 4. etc.
        // 5. create and return funcdecl node

        Token                           id = (Token) s2;
        ParseTree.TypeSpec              returnType = (ParseTree.TypeSpec) s4;
        ArrayList<ParseTree.Param>      params = (ArrayList<ParseTree.Param>) s6;
        ArrayList<ParseTree.LocalDecl>  localdecls = (ArrayList<ParseTree.LocalDecl>) s9;

        ParseTree.FuncDecl funcdecl = new ParseTree.FuncDecl(id.lexeme, returnType, params, localdecls, new ArrayList<ParseTree.Stmt>());

        ParseTreeInfo.FuncDeclInfo funcInfo = new ParseTreeInfo.FuncDeclInfo();

        funcInfo.setName(id.lexeme);
        env.Put(id.lexeme + "func", id.lexeme + "()");
        funcInfo.setType(returnType.typename);

        int paramsSize = 0;

        for (ParseTree.Param param : params) {
            env.Put(param.ident, param.typespec.typename);
            env.Put(param.ident+"paramType", param.typespec.typename);
            env.Put("param" + paramsSize, param.typespec.typename);
            paramsSize++;
        }


        Env newEnv = new Env(env);
        env = newEnv;
        env.Put("returnType", returnType.typename);
        env.Put("function", id.lexeme);
        if (!(paramsSize == 0)) {
            env.Put(id.lexeme+"expectedParamsSize", paramsSize);
        } else {
            env.Put(id.lexeme+"expectedParamsSize", 0);
        }
        env.Put(id.lexeme, id.lexeme);
        funcdecl.info.setName(funcdecl.ident);
        funcdecl.info.setType(funcdecl.rettype.typename);


        return funcdecl;
    }
    Object fundecl____FUNC_IDENT_TYPEOF_typespec_LPAREN_params_RPAREN_BEGIN_localdecls_X10_stmtlist_END(Object s1, Object s2, Object s3, Object s4, Object s5, Object s6, Object s7, Object s8, Object s9, Object s10, Object s11, Object s12) throws Exception
    {
        // 1. check if this function has at least one return type
        // 2. etc.
        // 3. create and return funcdecl node
        Token                            id         = (Token                           )s2;
        ParseTree.TypeSpec               rettype    = (ParseTree.TypeSpec              )s4;
        ArrayList<ParseTree.Param>       params     = (ArrayList<ParseTree.Param>      )s6;
        ArrayList<ParseTree.LocalDecl>   localdecls = (ArrayList<ParseTree.LocalDecl>  )s9;
        ArrayList<ParseTree.Stmt>        stmtlist   = (ArrayList<ParseTree.Stmt>       )s11;
        Token                            end        = (Token                           )s12;
        ParseTree.FuncDecl funcdecl = new ParseTree.FuncDecl(id.lexeme, rettype, params, localdecls, stmtlist);
        String functionName = (String) env.Get("function");
        if (localdecls.isEmpty() && stmtlist.isEmpty()) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Function " + functionName + "() should return at least one value.");
        }
        return funcdecl;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Object params____paramlist(Object s1) throws Exception ///NOT DONE
    {
        ArrayList<ParseTree.Param> paramList = (ArrayList<ParseTree.Param>) s1;
        return paramList;
    }

    Object params____eps() throws Exception
    {
        return new ArrayList<ParseTree.Param>();
    }
    Object paramlist____paramlist_COMMA_param(Object s1, Object s2, Object s3) throws Exception
    {
        ArrayList<ParseTree.Param> paramlist = (ArrayList<ParseTree.Param>)s1;
        ParseTree.Param            param     = (ParseTree.Param           )s3;
        paramlist.add(param);
        param.info.setType(env.Get(param.ident).toString());
        return paramlist;
    }

    Object paramlist____param(Object s1) throws Exception
    {
        ArrayList<ParseTree.Param> paramList = new ArrayList<ParseTree.Param>();
        paramList.add((ParseTree.Param) s1);
        return paramList;
    }

    Object param____IDENT_TYPEOF_typespec(Object s1, Object s2, Object s3) throws Exception
    {
        Token id = (Token) s1;
        ParseTree.TypeSpec typespec = (ParseTree.TypeSpec)s3;
        ParseTree.Param param = new ParseTree.Param(id.lexeme, typespec);
        env.Put(id.lexeme, typespec.typename);
        typespec.info.setType(id.lexeme);
        param.reladdr = 1;
        return param;

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object stmtlist____stmtlist_stmt(Object s1, Object s2) throws Exception
    {
        ArrayList<ParseTree.Stmt> stmtlist = (ArrayList<ParseTree.Stmt>)s1;
        ParseTree.Stmt            stmt     = (ParseTree.Stmt           )s2;
        stmtlist.add(stmt);
        return stmtlist;
    }
    Object stmtlist____eps() throws Exception
    {
        return new ArrayList<ParseTree.Stmt>();
    }

    Object stmt____assignstmt  (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.AssignStmt);
        return s1;
    }
    Object stmt____printstmt (Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.PrintStmt);
        return s1;
    }
    Object stmt____returnstmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.ReturnStmt);
        return s1;


    }
    Object stmt____ifstmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.IfStmt);
        return s1;
    }
    Object stmt____whilestmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.WhileStmt);
        return s1;
    }
    Object stmt____compoundstmt(Object s1) throws Exception
    {
        assert(s1 instanceof ParseTree.CompoundStmt);
        return s1;
    }
    Object print_stmt____PRINT_expr_SEMI(Object s1, Object s2, Object s3) throws Exception
    {
        ParseTree.Expr expr = (ParseTree.Expr)s2;
        return new ParseTree.PrintStmt(expr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object assignstmt____IDENT_ASSIGN_expr_SEMI(Object s1, Object s2, Object s3, Object s4) throws Exception {
        Token id = (Token)s1;
        ParseTree.Expr expr = (ParseTree.Expr)s3;
        ParseTree.AssignStmt assignstmt = new ParseTree.AssignStmt(id.lexeme, expr);
        assignstmt.ident_reladdr = 1;

        Object typeInfo = env.Get(id.lexeme);
        if (typeInfo == null) {
            throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] Variable " + id.lexeme + " is not defined.");
        }

        if (expr instanceof ParseTree.ExprIdent) {
            ParseTree.ExprIdent exprIdent = (ParseTree.ExprIdent) expr;
            if (env.Get(exprIdent.ident) == null) {
                throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] Variable " + exprIdent.ident + " is not defined.");
            }
        }

        String exprType = expr.info.getType();
        if (exprType.equals("bool") && typeInfo.equals("num")) {
            throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] Variable " + id.lexeme + " should have " + typeInfo + " value, instead of " + exprType + " value.");
        }
        if (exprType.equals("num") && typeInfo.equals("bool")) {
            throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] Variable " + id.lexeme + " should have " + typeInfo + " value, instead of " + exprType + " value.");
        }

        if (expr instanceof ParseTree.ExprNumLit) {
            Object exprVal = ((ParseTree.ExprNumLit) expr).val;
            env.Put(id.lexeme, exprVal);
        } else if (expr instanceof ParseTree.ExprBoolLit) {
            Object exprVal = ((ParseTree.ExprBoolLit) expr).val;
            env.Put(id.lexeme, exprVal);
        }

        return assignstmt;
    }


    Object assignstmt____IDENT_LBRACKET_expr_RBRACKET_ASSIGN_expr_SEMI(Object s1, Object s2, Object s3, Object s4, Object s5, Object s6, Object s7) throws Exception {
        Token id = (Token)s1; // the identifier for the array
        Token LBRACKET = (Token)s2;
        ParseTree.Expr indexExpr = (ParseTree.Expr)s3; // the index expression
        ParseTree.Expr valueExpr = (ParseTree.Expr)s6; // the expression to assign

        String fullType = (String) env.Get(id.lexeme);

        setTypeBasedOnVal(indexExpr);

        if (env.Get(id.lexeme) == null) {
            throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] Array " + id.lexeme + " is not defined.");
        }
        if (!fullType.endsWith("[]")) {
            throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] Identifier " + id.lexeme + " should be array variable.");
        }
        String baseType = fullType.substring(0, fullType.length() - 2);
        if (!valueExpr.info.getType().equals(baseType)) {
            throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] Element of array " + id.lexeme + " should have " + baseType + " value, instead of " + valueExpr.info.getType() + " value.");
        }

        if (!indexExpr.info.getType().equals("num")) {
            throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] Array index must be num value.");
        }

        ParseTree.AssignStmtForArray assignStmt = new ParseTree.AssignStmtForArray(id.lexeme, indexExpr, valueExpr);
        assignStmt.ident_reladdr = 1;
        return assignStmt;
    }

    Object returnstmt____RETURN_expr_SEMI(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr.value_type matches with the current function return type
        // 2. etc.
        // 3. create and return node
        //ParseTree.TypeSpec returnType =
        ParseTree.Expr expr = (ParseTree.Expr)s2;
        String exprType = expr.info.getType();
        String expectedReturnType = (String) env.Get("returnType");
        String functionName = (String) env.Get("function");

        Object vars = env.Get(expr.info.getType());

        if (exprType.isEmpty() && !expectedReturnType.isEmpty()) {
            throw new Exception("[Error at " + expr.info.getLineno() + ":" + expr.info.getColumn() + "] Function " + functionName + "() should return at least one value.");
        }

        if (!exprType.equals(expectedReturnType)) {
            throw new Exception("[Error at " + expr.info.getLineno() + ":" + expr.info.getColumn() + "] Function " + functionName + "() should return " + expectedReturnType + " value, instead of " + exprType + " value.");
        }

        //String expectedReturnType =

        return new ParseTree.ReturnStmt(expr);

      //  String exprType = expr.info.getType();

       // String expectedReturnType = parseTreeInfo.getReturnType();

        // Check if the expression type matches with the function's return type
      //  if (!exprType.equals(expectedReturnType)) {
        //    throw new Exception("Return type mismatch: Expected " + exprType + ", but found " + expectedReturnType);
       // }


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object localdecls____localdecls_localdecl(Object s1, Object s2)
    {
        ArrayList<ParseTree.LocalDecl> localdecls = (ArrayList<ParseTree.LocalDecl>)s1;
        ParseTree.LocalDecl            localdecl  = (ParseTree.LocalDecl           )s2;
        localdecls.add(localdecl);
        return localdecls;
    }
    Object localdecls____eps() throws Exception
    {
        return new ArrayList<ParseTree.LocalDecl>();
    }
    Object localdecl____VAR_IDENT_TYPEOF_typespec_SEMI(Object s1, Object s2, Object s3, Object s4, Object s5) throws Exception
    {
        Token              id       = (Token             )s2;
        ParseTree.TypeSpec typespec = (ParseTree.TypeSpec)s4;
        ParseTree.LocalDecl localdecl = new ParseTree.LocalDecl(id.lexeme, typespec);

        Object tokenid = env.Get(id.lexeme);
        if (tokenid != null) {
            throw new Exception("[Error at " + id.lineno + ":" + id.column + "] Identifier " + id.lexeme + " is already defined.");
        }

        typespec.info.setType(id.lexeme);
        env.Put(id.lexeme, typespec.typename);

        return localdecl;
    }

    Object args____eps() throws Exception
    {
        return new ArrayList<ParseTree.Expr>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object if_stmt____IF_expr_THEN_stmt_list_ELSE_stmt_list_END(Object s1, Object s2, Object s3, Object s4, Object s5, Object s6, Object s7) throws Exception {
        ParseTree.Expr condition = (ParseTree.Expr) s2;
        ArrayList<ParseTree.Stmt> thenStmtList = (ArrayList<ParseTree.Stmt>) s4;
        ArrayList<ParseTree.Stmt> elseStmtList = (ArrayList<ParseTree.Stmt>) s6;

        // Check if the condition expression type is boolean
        String conditionType = condition.info.getType();
        if (!conditionType.equals("bool")) {
            throw new Exception("[Error at " + condition.info.getLineno() + ":" + condition.info.getColumn() + "] Condition of if or while statement should be bool value.");
        }

        return new ParseTree.IfStmt(condition, thenStmtList, elseStmtList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object while_stmt____WHILE_expr_BEGIN_stmt_list_END(Object s1, Object s2, Object s3, Object s4, Object s5) throws Exception {
        ParseTree.Expr expr = (ParseTree.Expr) s2;
        ArrayList<ParseTree.Stmt> stmtList = (ArrayList<ParseTree.Stmt>) s4;

        setTypeBasedOnVal(expr);

        // Ensure the condition expression is a boolean
        String exprType = expr.info.getType();
        if (!exprType.equals("bool")) {
            throw new Exception("[Error at " + expr.info.getLineno() + ":" + expr.info.getColumn() + "] While loop condition must be a boolean expression, but got " + exprType + " instead.");
        }

        // Ensure the while loop body is not empty
        if (stmtList.isEmpty()) {
            throw new Exception("[Error at " + expr.info.getLineno() + ":" + expr.info.getColumn() + "] While loop body cannot be empty.");
        }

        return new ParseTree.WhileStmt(expr, stmtList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object compound_stmt____BEGIN_local_decls_stmt_list_END(Object s1, Object s2, Object s3, Object s4) {
        enterBlock();

        ArrayList<ParseTree.LocalDecl> localDecls = (ArrayList<ParseTree.LocalDecl>) s2;
        ArrayList<ParseTree.Stmt> stmtList = (ArrayList<ParseTree.Stmt>) s3;

        exitBlock();
        return new ParseTree.CompoundStmt(localDecls, stmtList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object args____arg_list(Object s1) throws Exception
    {
        ArrayList<ParseTree.Arg> arglist = (ArrayList<ParseTree.Arg>)s1;
        return arglist;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object arg_list____arg_list_COMMA_expr(Object s1, Object s2, Object s3) {
        ArrayList<ParseTree.Arg> argList = (ArrayList<ParseTree.Arg>) s1;
        ParseTree.Expr expr = (ParseTree.Expr) s3;
        argList.add(new ParseTree.Arg(expr));
        return argList;
    }

    Object arg_list____expr(Object s1) throws Exception {
        ArrayList<ParseTree.Arg> arglist = new ArrayList<ParseTree.Arg>();
        arglist.add(new ParseTree.Arg((ParseTree.Expr)s1));
        return arglist;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Object expr____expr_ADD_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "+");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        /*expr1.info.setColumn(oper.column);
        expr2.info.setLineno(oper.lineno);*/

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprAdd result = new ParseTree.ExprAdd(expr1, expr2);
        result.info.setType("num");

        return result;
    }
    Object expr____expr_SUB_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "-");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprSub result = new ParseTree.ExprSub(expr1, expr2);

        result.info.setType("num");

        return result;
    }
    Object expr____expr_MUL_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;

        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "*");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprMul result = new ParseTree.ExprMul(expr1, expr2);

        result.info.setType("num");

        return result;
    }
    Object expr____expr_DIV_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "/");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprDiv result = new ParseTree.ExprDiv(expr1, expr2);

        result.info = new ParseTreeInfo.ExprInfo();
        result.info.setType("num");

        return result;
    }
    Object expr____expr_MOD_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "%");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprMod result = new ParseTree.ExprMod(expr1, expr2);

        result.info.setType("num");

        return result;
    }
    Object expr____expr_EQ_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "=");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprEq result = new ParseTree.ExprEq(expr1, expr2);

        result.info.setType("bool");

        return result;
    }
    Object expr____expr_NE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "!=");
        Object operType = env.Get(oper.lexeme);


        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprNe result = new ParseTree.ExprNe(expr1, expr2);
        result.info.setType("bool");
        return result;
    }
    Object expr____expr_LE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "<=");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprLe result = new ParseTree.ExprLe(expr1, expr2);
        result.info.setType("bool");
        return result;
    }
    Object expr____expr_LT_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "<");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprLt result = new ParseTree.ExprLt(expr1, expr2);
        result.info.setType("bool");
        return result;
    }
    Object expr____expr_GE_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, ">=");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprGe result = new ParseTree.ExprGe(expr1, expr2);
        result.info.setType("bool");
        return result;
    }
    Object expr____expr_GT_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        ParseTree.ExprIdent exprIdent = (ParseTree.ExprIdent) expr1;

        Object value = env.Get(exprIdent.ident);

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, ">");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprGt result = new ParseTree.ExprGt(expr1,expr2);

        result.info.setType("bool");

        return result;
    }
    Object expr____expr_AND_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "and");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprNumLit) && (operType.equals("and")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("and")) && (expr2 instanceof ParseTree.ExprNumLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprAnd result = new ParseTree.ExprAnd(expr1, expr2);
        result.info.setType("bool");
        return result;
    }
    Object expr____expr_OR_expr(Object s1, Object s2, Object s3) throws Exception
    {
        // 1. check if expr1.value_type matches with the expr2.value_type
        // 2. etc.
        // 3. create and return node that has value_type
        ParseTree.Expr expr1 = (ParseTree.Expr)s1;
        Token          oper  = (Token         )s2;
        ParseTree.Expr expr2 = (ParseTree.Expr)s3;
        // check if expr1.type matches with expr2.type

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "or");
        Object OperType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprNumLit) && (OperType.equals("and")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) && (OperType.equals("and")) && (expr2 instanceof ParseTree.ExprNumLit))
        {
            throw new Exception("[Error at " + ((Token) s2).lineno + ":" + ((Token) s2).column + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        }

        ParseTree.ExprOr result = new ParseTree.ExprOr(expr1, expr2);
        result.info.setType("bool");
        return result;
    }
    Object expr____NOT_expr(Object s1, Object s2) throws Exception
    {
        Token          oper  = (Token         )s1;
        ParseTree.Expr expr  = (ParseTree.Expr)s2;
        String type = String.valueOf(expr instanceof ParseTree.ExprNumLit ? "num" : "bool");
        env.Put(oper.lexeme, "not");
        Object OperType = env.Get(oper.lexeme);
        setTypeBasedOnVal(expr);
        if((OperType.equals("not")) && (expr instanceof ParseTree.ExprNumLit))
        {
            throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] Unary operation " + oper.lexeme + " cannot be used with " + type + " value.");
        }
        ParseTree.ExprNot result = new ParseTree.ExprNot(expr);
        result.info.setType("bool");
        return result;
    }
    Object expr____LPAREN_expr_RPAREN(Object s1, Object s2, Object s3) throws Exception
    {
        Token        lparen = (Token         )s1;
        ParseTree.Expr expr = (ParseTree.Expr)s2;
        Token        rparen = (Token         )s3;
        return new ParseTree.ExprParen(expr);
    }
    Object expr____IDENT(Object s1) throws Exception
    {
        Token id = (Token)s1;
        ParseTree.ExprIdent result = new ParseTree.ExprIdent(id.lexeme);
        result.reladdr = 1;

        Object type = env.Get(id.lexeme);
        if (type == null) {
            throw new Exception("[Error at " + id.lineno + ":" + id.column + "] Variable " + id.lexeme + " is not defined.");
        }

        Object typeArray = env.Get(id.lexeme+"func");

        if (typeArray == null) {
            typeArray = "placeholder";
        }
        if (!type.equals(typeArray) && !typeArray.equals("placeholder")) {
            throw new Exception("[Error at " + id.lineno + ":" + id.column + "] Identifier " + type + " should be non-function type.");
        }

        result.info.setType(type.toString());

        return result;
    }
    Object expr____NUMLIT(Object s1) throws Exception
    {
        Token num = (Token)s1;
        Double value = Double.parseDouble(num.lexeme);
        env.Put(num.lexeme, value);
        ParseTree.ExprNumLit result = new ParseTree.ExprNumLit(value);
        result.info.setType("num");
        result.info.setValue(num.lexeme);
        result.info.setColumn(num.column);
        result.info.setLineno(num.lineno);
        return result;
    }
    Object expr____BOOLLIT(Object s1) throws Exception
    {
        Token token = (Token)s1; // Token containing boolean literal
        boolean value = Boolean.parseBoolean(token.lexeme);
        ParseTree.ExprBoolLit expr = new ParseTree.ExprBoolLit(value);
        expr.info.setType("bool");
        expr.info.setValue(token.lexeme);
        expr.info.setColumn(token.column);
        expr.info.setLineno(token.lineno);
        return expr;
    }
    Object expr____IDENT_LPAREN_args_RPAREN(Object s1, Object s2, Object s3, Object s4) throws Exception
    {
        Token id = (Token)s1;
        ArrayList<ParseTree.Arg> args = (ArrayList<ParseTree.Arg>)s3;

        Integer expectedParams = (Integer) env.Get(id.lexeme + "expectedParamsSize");
        if (expectedParams == null) {
            expectedParams = 0;
        }
        Object funcDefined = env.Get(id.lexeme);
        //ParseTreeInfo.FuncDeclInfo funcInfo = (ParseTreeInfo.FuncDeclInfo) env.Get(id.lexeme);

        /*if (funcDefined == null) {
            throw new Exception("should be function.");
        }*/

        if (!id.lexeme.equals(funcDefined)) {
            throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] Function " + id.lexeme + "() is not defined.");
        }

        if (args.size() != expectedParams) {
            throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] Function " + id.lexeme + "() should be called with the correct number of arguments.");
        }

        for (int i = 0; i < args.size(); i++) {
            ParseTree.Arg actualArg = args.get(i);
            Object expectedArg = env.Get("param"+i);
            if (!actualArg.expr.info.getType().equals(expectedArg)) {
                throw new Exception("[Error at " + ((Token) s1).lineno + ":" + ((Token) s1).column + "] The " + (i+1) + ordinalSuffix(i + 1) + " argument of function " + funcDefined + "() should be a " + expectedArg + " value, instead of a " + actualArg.expr.info.getType() + " value.");
            }
        }


        ParseTree.ExprFuncCall result = new ParseTree.ExprFuncCall(id.lexeme, args);
        return result;
    }
    Object expr____NEW_primtype_LBRACKET_expr_RBRACKET(Object s1, Object s2, Object s3, Object s4, Object s5) throws Exception
    {
        ParseTree.TypeSpec primtype = (ParseTree.TypeSpec)s2;
        ParseTree.Expr expr = (ParseTree.Expr)s4;
        Object value = expr.info.getType();
        if(value.equals("true") || value.equals("false"))
        {
            value = "bool";
        }
        if(value.equals("bool"))
        {
            throw new Exception("[Error at " + ((Token) s2).column + ":" + ((Token) s2).lineno + "] Array size must be a num value.");
        }
        ParseTree.ExprNewArray result = new ParseTree.ExprNewArray(primtype, expr);
        return result;
    }
    Object expr____IDENT_LBRACKET_expr_RBRACKET(Object s1, Object s2, Object s3, Object s4) throws Exception {
        Token id = (Token)s1;
        Token LBRACKET = (Token) s2;
        ParseTree.Expr indexExpr = (ParseTree.Expr)s3;

        setTypeBasedOnVal(indexExpr);
        String indexType = indexExpr.info.getType();
        Object fullType = env.Get(id.lexeme);

        if (!fullType.toString().endsWith("[]")) {
            throw new Exception("[Error at " + id.lineno + ":" + (LBRACKET.column+1) + "] Identifier " + id.lexeme + " should be array variable.");
        }

        if (!indexType.equals("num")) {
            throw new Exception("[Error at " + id.lineno + ":" + (LBRACKET.column+1) + "] Array index must be num value.");
        }

        ParseTree.ExprArrayElem result = new ParseTree.ExprArrayElem(id.lexeme, indexExpr);
        result.reladdr = 1;
        return result;
    }
    Object expr____IDENT_DOT_SIZE(Object s1, Object s2, Object s3) throws Exception
    {
        Token id = (Token)s1;
        ParseTree.ExprArraySize result = new ParseTree.ExprArraySize(id.lexeme);
        result.reladdr = 1;
        return result;
    }
}