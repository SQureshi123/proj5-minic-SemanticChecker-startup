import java.util.ArrayList;
import java.util.HashMap;

public class Env
{
    private HashMap<String, Object> symbols;
    public Env prev;
    public Env(Env prev)
    {
        this.prev = prev;
        symbols = new HashMap<>();
    }
    public void Put(String name, Object value)
    {
        symbols.put(name, value);
    }
    public Object Get(String name)
    {
        Object value = symbols.get(name);
        if (value != null) {
            //System.out.println("ENV VALUE: " + value);
            return value;
        } else if (prev != null) {
            return prev.Get(name);
        } else {
            return null;
        }
    }
}
