import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-item-summary',
  templateUrl: './item-summary.component.html',
  styleUrls: ['./item-summary.component.css']
})
export class ItemSummaryComponent implements OnInit {
  dataLocalUrl: any;

  constructor(
    private domSanitizer: DomSanitizer,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
  }

  loadReport() {
    this.fetchItemReport().subscribe((response: any) => {
      const blob = new Blob([response], { type: 'application/pdf' });
      const file = new File([blob], 'item-summary.pdf', {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      this.dataLocalUrl = this.domSanitizer.bypassSecurityTrustResourceUrl(fileURL);
    });
  }

  public fetchItemReport() {
    const httpOptions = {
      responseType: 'arraybuffer' as 'json'
    };
    return this.http.get(`http://localhost:9090/item-report`,httpOptions);
  }

}
