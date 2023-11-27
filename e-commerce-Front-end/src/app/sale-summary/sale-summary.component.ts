import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-sale-summary',
  templateUrl: './sale-summary.component.html',
  styleUrls: ['./sale-summary.component.css']
})
export class SaleSummaryComponent implements OnInit {

  dataLocalUrl: any;

  constructor(
    private domSanitizer: DomSanitizer,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
  }

  loadReport() {
    this.fetchSaleReport().subscribe((response: any) => {
      const blob = new Blob([response], { type: 'application/pdf' });
      const file = new File([blob], 'sale-summary.pdf', {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      this.dataLocalUrl = this.domSanitizer.bypassSecurityTrustResourceUrl(fileURL);
    });
  }

  public fetchSaleReport() {
    const httpOptions = {
      responseType: 'arraybuffer' as 'json'
    };
    return this.http.get(`http://localhost:9090/sale-report`,httpOptions);
  }

}
