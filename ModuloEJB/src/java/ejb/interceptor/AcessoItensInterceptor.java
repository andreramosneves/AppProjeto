package ejb.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class AcessoItensInterceptor {

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        System.out.println("Acesso Itens: " + context.getMethod());
        return context.proceed();
    }
}
