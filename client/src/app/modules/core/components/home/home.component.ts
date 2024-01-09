import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FlashcardsListResponse } from '../../models/auth.model';
import { environment } from '../../../../../environments/environment.development';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  apiUrl = `${environment.apiUrl}/flashcards`;
  flashcardsList: FlashcardsListResponse[] | undefined;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const headers = new HttpHeaders({
      Authorization: '' + localStorage.getItem('JWT_TOKEN'),
      'Content-Type': 'application/json',
    });

    this.http
      .get<FlashcardsListResponse[]>(`${this.apiUrl}/list`, {
        headers,
      })
      .subscribe((data) => {
        this.flashcardsList = data;
      });
  }
}
