import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMeta, NewMeta } from '../meta.model';

export type PartialUpdateMeta = Partial<IMeta> & Pick<IMeta, 'id'>;

type RestOf<T extends IMeta | NewMeta> = Omit<T, 'dataMeta'> & {
  dataMeta?: string | null;
};

export type RestMeta = RestOf<IMeta>;

export type NewRestMeta = RestOf<NewMeta>;

export type PartialUpdateRestMeta = RestOf<PartialUpdateMeta>;

export type EntityResponseType = HttpResponse<IMeta>;
export type EntityArrayResponseType = HttpResponse<IMeta[]>;

@Injectable({ providedIn: 'root' })
export class MetaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/metas');

  create(meta: NewMeta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(meta);
    return this.http.post<RestMeta>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(meta: IMeta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(meta);
    return this.http
      .put<RestMeta>(`${this.resourceUrl}/${this.getMetaIdentifier(meta)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(meta: PartialUpdateMeta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(meta);
    return this.http
      .patch<RestMeta>(`${this.resourceUrl}/${this.getMetaIdentifier(meta)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMeta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMeta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMetaIdentifier(meta: Pick<IMeta, 'id'>): number {
    return meta.id;
  }

  compareMeta(o1: Pick<IMeta, 'id'> | null, o2: Pick<IMeta, 'id'> | null): boolean {
    return o1 && o2 ? this.getMetaIdentifier(o1) === this.getMetaIdentifier(o2) : o1 === o2;
  }

  addMetaToCollectionIfMissing<Type extends Pick<IMeta, 'id'>>(
    metaCollection: Type[],
    ...metasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const metas: Type[] = metasToCheck.filter(isPresent);
    if (metas.length > 0) {
      const metaCollectionIdentifiers = metaCollection.map(metaItem => this.getMetaIdentifier(metaItem));
      const metasToAdd = metas.filter(metaItem => {
        const metaIdentifier = this.getMetaIdentifier(metaItem);
        if (metaCollectionIdentifiers.includes(metaIdentifier)) {
          return false;
        }
        metaCollectionIdentifiers.push(metaIdentifier);
        return true;
      });
      return [...metasToAdd, ...metaCollection];
    }
    return metaCollection;
  }

  protected convertDateFromClient<T extends IMeta | NewMeta | PartialUpdateMeta>(meta: T): RestOf<T> {
    return {
      ...meta,
      dataMeta: meta.dataMeta?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restMeta: RestMeta): IMeta {
    return {
      ...restMeta,
      dataMeta: restMeta.dataMeta ? dayjs(restMeta.dataMeta) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMeta>): HttpResponse<IMeta> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMeta[]>): HttpResponse<IMeta[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
