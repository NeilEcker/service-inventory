import http from '@/http';

export default class ApiService {

    public static login(user: any) {
        return http({url: '/login', data: user, method: 'POST'});
    }

    public static getReviewsDue() {
        return http.get('/appService/reviewDue');
    }

    public static getBusinessUnits() {
        return http.get('/businessUnit');
    }

    public static getDivisions() {
        return http.get('/division');
    }

}
