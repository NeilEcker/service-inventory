import http from '@/http';

export default class ReferenceApiService {

    public static getReferences() {
        return http.get('/reference');
    }

    public static getReference(id: string) {
        return http.get(`/reference/${id}`);
    }

    public static saveReference(application: string, reference: any) {
        return http.post(`/appService/${application}/reference`, reference);
    }

    public static updateReference(id: string, reference: any) {
        return http.put(`/reference/${id}`, reference);
    }

    public static deleteReference(id: string) {
        return http.delete(`/reference/${id}`);
    }

    public static getReferenceTypes() {
        return http.get('/referenceType');
    }

}
