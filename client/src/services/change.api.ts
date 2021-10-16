import http from '@/http';

export default class ChangeApiService {

    public static getChanges() {
        return http.get('/change');
    }

    public static getChange(id: string) {
        return http.get(`/change/${id}`);
    }

    public static saveChange(change: any) {
        return http.post(`/change`, change);
    }

    public static updateChange(id: string, change: any) {
        return http.put(`/change/${id}`, change);
    }

    public static deleteChange(id: string) {
        return http.delete(`/change/${id}`);
    }

    /* public static saveApproval(approval: any) {
        return http.post(`/changeApproval`, approval);
    }

    public static updateApproval(approval: any) {
        return http.post(`/changeApproval`, approval);
    }*/

    public static deleteApproval(id: string) {
        return http.delete(`/changeApproval/${id}`);
    }

    public static getChangesRecent() {
        return http.get('/change/recent');
    }

    public static getChangesUpcoming() {
        return http.get('/change/upcoming');
    }

    public static getChangesByAppService(id: string) {
        return http.get(`/change/${id}/changes`);
    }

    public static getChangesByServer(id: string) {
        return http.get(`/change/${id}/serverChanges`);
    }
}
