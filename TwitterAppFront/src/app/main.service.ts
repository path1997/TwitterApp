import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Keyword} from "./keyword";
import {Tweet} from "./tweet";
import {Author} from "./author";

@Injectable({
  providedIn: 'root'
})
export class MainService {

  private baseURL = "http://127.0.0.1:8080/api/v1";
  constructor(private httpClient: HttpClient) { }

  getKeywordsList() :Observable<Keyword[]>{
    return this.httpClient.get<Keyword[]>(`${this.baseURL}/keywords`);
  }
  createKeyword(keywordString: String): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/keywords`,keywordString);
  }
  getTweetsListById(id: string) :Observable<Tweet[]>{
    return this.httpClient.get<Tweet[]>(`${this.baseURL}/tweets/${id}`);
  }

  deleteKeyword(id: string) :Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/keyword/delete`,id);
  }

  getTweetsByAuthorName(name: string) :Observable<Tweet[]>{
    return this.httpClient.get<Tweet[]>(`${this.baseURL}/tweetsByAuthorName/${name}`);
  }

  addAuthorToFavourite(id: string) :Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/addAuthorToFavourite`,id);
  }

  deleteTweetById(id: string) :Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/deleteTweet`,id);
  }

  getFavouriteAuthors() :Observable<Author[]>{
    return this.httpClient.get<Author[]>(`${this.baseURL}/favouriteAuthors`);
  }

  deleteFavouriteAuthor(id: string) :Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/favouriteAuthor/delete`,id);
  }

  getTweetsByAuthorId(id: string) :Observable<Tweet[]>{
    return this.httpClient.get<Tweet[]>(`${this.baseURL}/favouriteAuthor/${id}`);
  }

  getDeletedList() :Observable<Tweet[]> {
    return this.httpClient.get<Tweet[]>(`${this.baseURL}/trashTweets`)
  }

  deletePermamentTweetById(id: string) :Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/deleteTweetPermment`,id);
  }

  revertDeleteTweetById(id: string) :Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/revertDeletedTweet`,id);
  }

  refleshData() :Observable<Object>{
    return this.httpClient.get(`${this.baseURL}/refleshData`)
  }
}
