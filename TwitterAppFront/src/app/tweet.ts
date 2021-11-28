import {Author} from "./author";
import {Keyword} from "./keyword";

export class Tweet{
  id :string;
  createdAt: string;
  text: string;
  lang: string;
  author: Author;
  keyword: Keyword;
}
