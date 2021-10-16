import http from '@/http';

export default class DocumentApiService {

    public static search(q: string) {
        return http.get('/document/search?q=' + q);
    }

    public static content(node: string) {
        return http.get('/document/content?node=' + node);
    }

    public static searchByTag(tag: string) {
        return http.get('/document/searchByTag?tag=' + tag);
    }

}
