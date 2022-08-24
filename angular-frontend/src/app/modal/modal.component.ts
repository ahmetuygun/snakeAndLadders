import { Component, OnInit } from '@angular/core';
import {MdbModalRef} from "mdb-angular-ui-kit/modal";
import {Router} from "@angular/router";

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {
  winner: string | null = null;

  constructor(public modalRef: MdbModalRef<ModalComponent>,private router: Router) { }

  ngOnInit(): void {
  }

  public goBack(){
    this.router.navigate(['home']);
  }

}
