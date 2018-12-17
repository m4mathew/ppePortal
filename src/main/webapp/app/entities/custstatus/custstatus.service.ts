import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICUSTSTATUS } from 'app/shared/model/custstatus.model';
import { ISUMMARY } from 'app/shared/model/summary.model';

type EntityResponseType = HttpResponse<ICUSTSTATUS>;
type EntityArrayResponseType = HttpResponse<ICUSTSTATUS[]>;

@Injectable({ providedIn: 'root' })
export class CUSTSTATUSService {
    private resourceUrl = SERVER_API_URL + 'api/custstatuses';
    private resourceUrlalt = SERVER_API_URL + 'api/custstatuses/latest';
    private resourceUrlSum = SERVER_API_URL + 'api/custstatuses/summary';
    
    constructor(private http: HttpClient) {}

    create(cUSTSTATUS: ICUSTSTATUS): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cUSTSTATUS);
        return this.http
            .post<ICUSTSTATUS>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(cUSTSTATUS: ICUSTSTATUS): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cUSTSTATUS);
        return this.http
            .put<ICUSTSTATUS>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICUSTSTATUS>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICUSTSTATUS[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)))
            .pipe(map((res: EntityArrayResponseType) => this.updateDays(res)));
    }
	
	findOnlyLatest(req?: any): Observable<EntityArrayResponseType> {
	   const options = createRequestOption(req);
        return this.http
            .get<ICUSTSTATUS[]>(this.resourceUrlalt, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)))
            .pipe(map((res: EntityArrayResponseType) => this.updateDays(res)));
    }
    
    findSummary(req?: any): Observable<EntityArrayResponseType> {
	   const options = createRequestOption(req);
        return this.http
            .get<ISUMMARY[]>(this.resourceUrlSum, { params: options, observe: 'response' });
    }
    
    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(cUSTSTATUS: ICUSTSTATUS): ICUSTSTATUS {
        const copy: ICUSTSTATUS = Object.assign({}, cUSTSTATUS, {
            changeDt: cUSTSTATUS.changeDt != null && cUSTSTATUS.changeDt.isValid() ? cUSTSTATUS.changeDt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.changeDt = res.body.changeDt != null ? moment(res.body.changeDt) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((cUSTSTATUS: ICUSTSTATUS) => {
            cUSTSTATUS.changeDt = cUSTSTATUS.changeDt != null ? moment(cUSTSTATUS.changeDt) : null;
        });
        return res;
    }
    private updateDays(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((cUSTSTATUS: ICUSTSTATUS) => {
            cUSTSTATUS.days = moment().diff(cUSTSTATUS.changeDt,'days')!= null ?moment().diff(cUSTSTATUS.changeDt,'days'):0;
        });
        return res;
    }

    	
	
}
