import http from '@/http';

export default class StaffApiService {

    public static getStaffMembers() {
        return http.get('/staff');
    }

    public static getStaff(id: string) {
        return http.get(`/staff/${id}`);
    }

    public static saveStaff(staff: any) {
        return http.post(`/staff`, staff);
    }

    public static updateStaff(id: string, staff: any) {
        return http.put(`/staff/${id}`, staff);
    }

    public static deleteStaff(id: string) {
        return http.delete(`/staff/${id}`);
    }
}
