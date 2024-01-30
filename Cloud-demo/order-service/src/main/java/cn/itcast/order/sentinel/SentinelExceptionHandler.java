package cn.itcast.order.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SentinelExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        String msg = "Unknown Exception";
        int status = 429;
        if(e instanceof FlowException){
           msg = "请求被限流";
        } else if (e instanceof ParamFlowException) {
           msg = "请求被热点参数限流";
        } else if (e instanceof DegradeException){
           msg = "请求被降级";
        } else if(e instanceof AuthorityException) {
           msg = "无访问权限";
           status = 401;
        }
        httpServletResponse.setStatus(status);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().println("{\"msg\": " + msg + ", \"status\": " + status + "}");
    }
}
