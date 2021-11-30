import { Component, OnInit } from '@angular/core';
import {MainService} from "../main.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-init-view',
  templateUrl: './init-view.component.html',
  styleUrls: ['./init-view.component.css']
})
export class InitViewComponent implements OnInit {

  constructor(private mainService: MainService,
              private router: Router) { }

  ngOnInit(): void {
  }

  refleshData() {
    this.mainService.refleshData().subscribe(data =>{
        console.log(data);
        this.ngOnInit()
      },
      error => console.log(error))
  }
}
