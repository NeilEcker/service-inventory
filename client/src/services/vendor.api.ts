import http from '@/http';

export default class VendorApiService {

    public static getVendors() {
        return http.get('/vendor');
    }

    public static getVendor(id: string) {
        return http.get(`/vendor/${id}`);
    }

    public static saveVendor(vendor: any) {
        return http.post(`/vendor`, vendor);
    }

    public static updateVendor(id: string, vendor: any) {
        return http.put(`/vendor/${id}`, vendor);
    }

    public static deleteVendor(id: string) {
        return http.delete(`/vendor/${id}`);
    }
}
