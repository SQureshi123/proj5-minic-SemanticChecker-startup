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
        System.out.println("Entered new block ---------------------------------------------------------------------------");
    }

    public void exitBlock() {
        if (env.prev != null) {
            env = env.prev;
            System.out.println("Exited previous block");
        } else {
            System.out.println("No previous block");
        }
    }

    private void setTypeBasedOnVal(ParseTree.Expr expr) {
        if (expr instanceof ParseTree.ExprIdent) {
            ParseTree.ExprIdent exprIdent = (ParseTree.ExprIdent) expr;
            System.out.println("function used for " + exprIdent.ident);

            Object value = env.Get(exprIdent.ident);
            System.out.println("Expr: " + exprIdent.ident + " Value: " + value.toString());

            try {
                Double.parseDouble(value.toString());
                expr.info.setValue(env.Get(exprIdent.ident).toString());
                expr.info.setType("num");
                System.out.println("set to num with value: " + value);
                if (value.toString().equals("num")) {
                    expr.info.setType("num");
                }
                return;
            } catch (NumberFormatException e) {
                boolean boolValue = Boolean.parseBoolean(value.toString());
                if (value.toString().equals("true") || value.toString().equals("false")) {
                    expr.info.setValue(env.Get(exprIdent.ident).toString());
                    System.out.println("set to bool with value: " + value);
                    expr.info.setType("bool");
                } else {
                    System.out.println("Val is not num or bool: " + value);
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
        System.out.println("PrimType: " + primtype.typename);
        return primtype;
    }
    Object typespec____primtype_LBRACKET_RBRACKET(Object s1, Object s2, Object s3) //NOT DONE
    {
        ParseTree.TypeSpec primtype = (ParseTree.TypeSpec)s1;
        ParseTree.TypeSpec arrayType = new ParseTree.TypeSpec(primtype.typename + "[]");
        System.out.println("ArrayType: " + arrayType.typename);
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

        System.out.println("FUNDECL STATEMENT");

        Token                           id = (Token) s2;
        ParseTree.TypeSpec              returnType = (ParseTree.TypeSpec) s4;
        ArrayList<ParseTree.Param>      params = (ArrayList<ParseTree.Param>) s6;
        ArrayList<ParseTree.LocalDecl>  localdecls = (ArrayList<ParseTree.LocalDecl>) s9;

        ParseTree.FuncDecl funcdecl = new ParseTree.FuncDecl(id.lexeme, returnType, params, localdecls, new ArrayList<ParseTree.Stmt>());

        ParseTreeInfo.FuncDeclInfo funcInfo = new ParseTreeInfo.FuncDeclInfo();

        funcInfo.setName(id.lexeme);
        funcInfo.setType(returnType.typename);

        for (ParseTree.Param param : params) {
            env.Put(param.ident, param.typespec.typename);
            env.Put(param.ident+"paramType", param.typespec.typename);
        }

        Env newEnv = new Env(env);
        env = newEnv;
        env.Put("returnType", returnType.typename);
        env.Put("function", id.lexeme);
        if (params.isEmpty()) {
            env.Put("expectedParamsSize", 0);
        } else {
            env.Put("expectedParamsSize", params.size());
        }
        env.Put(id.lexeme, id.lexeme);
        funcdecl.info.setName(funcdecl.ident);
        funcdecl.info.setType(funcdecl.rettype.typename);


        return funcdecl;
    }
    Object fundecl____FUNC_IDENT_TYPEOF_typespec_LPAREN_params_RPAREN_BEGIN_localdecls_X10_stmtlist_END(Object s1, Object s2, Object s3, Object s4, Object s5, Object s6, Object s7, Object s8, Object s9, Object s10, Object s11, Object s12) throws Exception
    {
        System.out.println("FUNDECL2 STATEMENT");
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
            throw new Exception("[Error at :] Function " + functionName + "() should return at least one value.");
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
        System.out.println("PARAMLIST COMMA PARAM: " + env.Get(param.ident).toString() + "_________________________");
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
        System.out.println("PARAM TYPEOF: " + id.lexeme + " " + typespec.typename);
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
            throw new Exception("[Error at :] Variable " + id.lexeme + " is not defined.");
        }

        if (expr instanceof ParseTree.ExprIdent) {
            ParseTree.ExprIdent exprIdent = (ParseTree.ExprIdent) expr;
            if (env.Get(exprIdent.ident) == null) {
                throw new Exception("[Error at :] Variable " + exprIdent.ident + " is not defined.");
            }
        }

        String exprType = expr.info.getType();
        if (exprType.equals("bool") && typeInfo.equals("num")) {
            throw new Exception("[Error at :] Variable " + id.lexeme + " should have " + typeInfo + " value, instead of " + exprType + " value.");
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
        ParseTree.Expr indexExpr = (ParseTree.Expr)s3; // the index expression
        ParseTree.Expr valueExpr = (ParseTree.Expr)s6; // the expression to assign

        String fullType = (String) env.Get(id.lexeme);

        setTypeBasedOnVal(indexExpr);

        if (env.Get(id.lexeme) == null) {
            throw new Exception("[Error at :] Array " + id.lexeme + " is not defined.");
        }
        if (!fullType.endsWith("[]")) {
            throw new Exception("[Error at :] Identifier " + id.lexeme + " should be array variable.");
        }
        String baseType = fullType.substring(0, fullType.length() - 2);
        if (!valueExpr.info.getType().equals(baseType)) {
            throw new Exception("[Error at :] Element of array " + id.lexeme + " should have " + baseType + " value, instead of " + valueExpr.info.getType() + " value.");
        }

        System.out.println("Index Expr: " + indexExpr);

        if (!indexExpr.info.getType().equals("num")) {
            throw new Exception("[Error at " + indexExpr.info.getLineno() + ":" + indexExpr.info.getColumn() + "] Array index must be num value.");
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
        System.out.println("Vars: " + vars);

        System.out.println("Expr Type: " + exprType);
        System.out.println("Expected Expr Type: " + expectedReturnType);

        if (exprType.isEmpty() && !expectedReturnType.isEmpty()) {
            throw new Exception("[Error at " + expr.info.getLineno() + ":" + expr.info.getColumn() + "] Function " + functionName + "() should return at least one value.");
        }

        if (!exprType.equals(expectedReturnType)) {
            throw new Exception("[Error at " + expr.info.getLineno() + ":" + expr.info.getColumn() + "] Function " + functionName + "() should return " + expectedReturnType + " value, instead of " + exprType + " value.");
        }

        //String expectedReturnType =

        return new ParseTree.ReturnStmt(expr);
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
            throw new Exception("[Error at :] Identifier " + id.lexeme + " is already defined.");
        }

        typespec.info.setType(id.lexeme);
        System.out.println("PUTTING INTO ENV TABLE WITH TYPENAME FOR: " + id.lexeme + " WITH TYPE: " + typespec.typename);
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

        System.out.println("Evaluating expression: " + expr.toString());
        System.out.println("Expression type: " + expr.info.getType());

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

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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

        System.out.println("VALUE: " + value);

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, ">");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        System.out.println(expr1.info.getType());
        System.out.println(expr1.info.getType());

        if((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("+")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) || (expr2 instanceof ParseTree.ExprBoolLit)) {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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

        System.out.println("Type of expr1: " + expr1.info.getType());
        System.out.println("Type of expr2: " + expr2.info.getType());

        setTypeBasedOnVal(expr1);
        setTypeBasedOnVal(expr2);

        env.Put(oper.lexeme, "and");
        Object operType = env.Get(oper.lexeme);

        String type1 = expr1.info.getType();
        String type2 = expr2.info.getType();

        if((expr1 instanceof ParseTree.ExprNumLit) && (operType.equals("and")) && (expr2 instanceof ParseTree.ExprBoolLit))
        {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) && (operType.equals("and")) && (expr2 instanceof ParseTree.ExprNumLit))
        {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
        } else if ((expr1 instanceof ParseTree.ExprBoolLit) && (OperType.equals("and")) && (expr2 instanceof ParseTree.ExprNumLit))
        {
            throw new Exception("[Error at " + expr1.info.getLineno() + ":" + expr1.info.getColumn() + "] Binary operation " + oper.lexeme + " cannot be used with " + type1 + " and " + type2 + " values.");
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
            throw new Exception("[Error at " + expr.info.getLineno() + ":" + expr.info.getColumn() + "] Unary operation " + oper.lexeme + " cannot be used with " + type + " value.");
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
        System.out.println("TYPE IN EXPR____IDENT: " + type);
        /*if (type == null) {
            throw new Exception("Identifier '" + id.lexeme + "' not defined.");
        }*/

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
        return result;
    }
    Object expr____BOOLLIT(Object s1) throws Exception
    {
        Token bool = (Token)s1;
        Boolean value = Boolean.parseBoolean(bool.lexeme);
        ParseTree.ExprBoolLit result = new ParseTree.ExprBoolLit(value);
        result.info.setType("bool");
        result.info.setValue(bool.lexeme);
        return result;
    }
    Object expr____IDENT_LPAREN_args_RPAREN(Object s1, Object s2, Object s3, Object s4) throws Exception
    {
        Token id = (Token)s1;
        ArrayList<ParseTree.Arg> args = (ArrayList<ParseTree.Arg>)s3;

        Integer expectedParams = (Integer) env.Get(id.lexeme + "expectedParams");
        Object expectedParamType = env.Get(id.lexeme + "paramType");
        System.out.println("EXPECTED PARAM TYPE: " + expectedParamType + "///////////////////////////");
        if (expectedParams == null) {
            expectedParams = 0;
        }
        Object funcDefined = env.Get(id.lexeme);
        System.out.println("FUNC DEFINED: " + funcDefined + "+++++++++++++++++++++++++");
        //ParseTreeInfo.FuncDeclInfo funcInfo = (ParseTreeInfo.FuncDeclInfo) env.Get(id.lexeme);

        if (!id.lexeme.equals(funcDefined)) {
            throw new Exception("[Error at :] Function " + id.lexeme + "() is not declared.");
        }
        if (args.size() != expectedParams) {
            throw new Exception("[Error at :] Function " + id.lexeme + "() should be called with the correct number of arguments.");
        }


        ParseTree.ExprFuncCall result = new ParseTree.ExprFuncCall(id.lexeme, args);
        return result;
    }
    Object expr____NEW_primtype_LBRACKET_expr_RBRACKET(Object s1, Object s2, Object s3, Object s4, Object s5) throws Exception
    {
        ParseTree.TypeSpec primtype = (ParseTree.TypeSpec)s2;
        ParseTree.Expr expr = (ParseTree.Expr)s4;
        Object value = expr.info.getType();
        System.out.println("value: " + expr.info.getType());
        if(value.equals("true") || value.equals("false"))
        {
            value = "bool";
        }
        if(value.equals("bool"))
        {
            throw new Exception("[Error at " + expr.info.getLineno() + ":" + expr.info.getColumn() + "] Array size must be a num value.");
        }
        ParseTree.ExprNewArray result = new ParseTree.ExprNewArray(primtype, expr);
        return result;
    }
    Object expr____IDENT_LBRACKET_expr_RBRACKET(Object s1, Object s2, Object s3, Object s4) throws Exception {
        Token id = (Token)s1;
        ParseTree.Expr indexExpr = (ParseTree.Expr)s3;

        setTypeBasedOnVal(indexExpr);
        String indexType = indexExpr.info.getType();
        System.out.println("Type of index: " + indexType);
        Object fullType = env.Get(id.lexeme);
        System.out.println("IDENT EXPR FULLTYPE: " + fullType + " ______________");

        if (!fullType.toString().endsWith("[]")) {
            throw new Exception("[Error at " + indexExpr.info.getLineno() + ":" + indexExpr.info.getColumn() + "] Identifier " + id.lexeme + " should be array variable.");
        }

        if (!indexType.equals("num")) {
            throw new Exception("[Error at " + indexExpr.info.getLineno() + ":" + indexExpr.info.getColumn() + "] Array index must be num value.");
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