package Library;
public class Route {
    private String path;
    private Class<?> actionClass;
    private String method;

    public Route(String path, Class<?> actionClass, String method) {
        this.path = path;
        this.actionClass = actionClass;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public Class<?> getActionClass() {
        return actionClass;
    }

    public String getMethod() {
        return method;
    }
}
