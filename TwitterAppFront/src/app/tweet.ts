import {Author} from "./author";
import {Keyword} from "./keyword";

export class Tweet{
  id :number;
  createdAt: string;
  text: string;
  lang: string;
  author: Author;
  keyword: Keyword;
}
