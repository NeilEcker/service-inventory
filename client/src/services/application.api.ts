import http from '@/http';

export default class ApplicationApiService {

    public static getAppServices() {
        return http.get('/appService');
    }

    public static getApplication(id: string) {
        return http.get(`/appService/${id}`);
    }

    public static saveApplication(application: any) {
        return http.post(`/appService`, application);
    }

    public static updateApplication(id: string, application: any) {
        return http.put(`/appService/${id}`, application);
    }

    public static deleteApplication(id: string) {
        return http.delete(`/appService/${id}`);
    }

    public static getDependents(id: string) {
        return http.get(`/appService/${id}/dependents`);
    }

    public static getServicesByVendor(id: string) {
        return http.get(`/appService/servicesByVendor/${id}`);
    }

    public static getServicesByServer(id: string) {
        return http.get(`/appService/servicesByServer/${id}`);
    }

    public static search(query: string) {
        return http.get(`/appService/search?query=${query}`);
    }

    public static getApplicationsByDatabase() {
        return http.get(`/appService/applicationsByDatabase`);
    }

    public static getApplicationsByServiceAccount() {
        return http.get(`/appService/applicationsByServiceAccount`);
    }

    public static getApplicationsByBusinessUnit() {
        return http.get(`/appService/applicationsByBusinessUnit`);
    }
}
