package com.huang.controller.wx;

import com.huang.model.response.ResponseModel;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * jsapi 演示接口的 controller.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 * @date 2020-04-25
 */
@AllArgsConstructor
@RestController
@RequestMapping("/wx/jsapi/{appid}")
public class WxJsapiController {

    private final WxMpService wxService;

    @GetMapping("/getJsapiInfo")
    public WxJsapiSignature getJsapiTicket(@PathVariable String appid,
                                        @RequestParam("url") String url) throws WxErrorException {
        return this.wxService.switchoverTo(appid).createJsapiSignature(url);
    }
}
