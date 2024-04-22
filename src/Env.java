import java.util.ArrayList;
import java.util.HashMap;

public class Env
{
    private HashMap<String, Object> symbols;
    public Env prev;
    private int variableCount = 0;
    public Env(Env prev)
    {
        this.symbols = new HashMap<>();
        this.prev = prev;
    }
    public Object Put(String name, Object value)
    {
        symbols.put(name, value);
        variableCount++;
        return value;
    }
    public Object Get(String name)
    {
        Object value = symbols.get(name);
        if (value != null) {
            return value;
        } else if (prev != null) {
            return prev.Get(name);
        } else {
            return null;
        }
    }

    public boolean isDeclaredInCurrentScope(String name) {
        return symbols.containsKey(name);
    }

    public int currentScopeSize() {
        return variableCount;
    }
}
