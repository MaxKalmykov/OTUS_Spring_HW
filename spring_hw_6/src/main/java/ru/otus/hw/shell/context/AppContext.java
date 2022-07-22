package ru.otus.hw.shell.context;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class AppContext {

    private AppState state = AppState.FREE;
    private Object ContextObj;
    private Class<?> ObjClazz;

    public AppState getState() {
        return state;
    }

    public <T> void setContext(T obj, AppState state) {
        if (obj == null) {
            throw new RuntimeException("Context object can't be empty.");
        }
        if (state == null) {
            throw new RuntimeException("Context state can't be empty.");
        }
        if (state == AppState.FREE) {
            throw new RuntimeException("Can't free context, because context object is not empty. Use free method.");
        }
        this.ContextObj = obj;
        this.ObjClazz = obj.getClass();
        this.state = state;
    }

    public void free() {
        ContextObj = null;
        ObjClazz = null;
        state = AppState.FREE;
    }

    public <T> T getContextObject(Class<T> clazz) {
        if (clazz.isInstance(ContextObj)) {
            return clazz.cast(ContextObj);
        } else {
           throw new RuntimeException("Can't cast " + ObjClazz.getName() + " to " + clazz.getName());
        }
    }

    public boolean isFreeContext() {
        return state == AppState.FREE;
    }

}
