import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Keyword} from "./keyword";
import {Tweet} from "./tweet";

@Injectable({
  providedIn: 'root'
})
export class MainService {

  private baseURL = "http://localhost:8080/api/v1";
  constructor(private httpClient: HttpClient) { }

  getKeywordsList() :Observable<Keyword[]>{
    return this.httpClient.get<Keyword[]>(`${this.baseURL}/keywords`);
  }
  createKeyword(keywordString: String): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/keywords`,keywordString);
  }
  getTweetsListById(id: number) :Observable<Tweet[]>{
    return this.httpClient.get<Tweet[]>(`${this.baseURL}/tweets/${id}`);
  }

  deleteKeyword(id: number) :Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/keyword/delete`,id);
  }
}
