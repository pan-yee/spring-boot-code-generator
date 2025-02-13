import axios from '@/api/axios'
import ApiBase from '@/api/apiBase'

class BaseApi {
    constructor() {
    }

    // 获取当前用户token
    public getLoginToken = (params: any) => {
        return axios({
            method: "post",
            url: "/auth/login/token",
            params
        });
    }

    // 获取当前用户信息
    public getLoginUserInfo = (params: any) => {
        return axios({
            method: "get",
            url: "/auth/current/user",
            params
        });
    }

    // 退出登陆
    public logOut = (params: any) => {
        return axios({
            method: "post",
            url: "/auth/logout/token",
            params
        });
    }

    // 懒加载获取树形地址
    public lazyGetAddressList = (params: any) => {
        return axios({
            method: "post",
            url: "/base/area/getListByParentCode",
            params
        });
    }

}

class WarehouseApi extends ApiBase {
    constructor(service: string, module: string) {
        super(service, module)
    }
}

export default {
    BaseApi, WarehouseApi 
}