/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PpeportalTestModule } from '../../../test.module';
import { CUSTSTATUSComponent } from 'app/entities/custstatus/custstatus.component';
import { CUSTSTATUSService } from 'app/entities/custstatus/custstatus.service';
import { CUSTSTATUS } from 'app/shared/model/custstatus.model';

describe('Component Tests', () => {
    describe('CUSTSTATUS Management Component', () => {
        let comp: CUSTSTATUSComponent;
        let fixture: ComponentFixture<CUSTSTATUSComponent>;
        let service: CUSTSTATUSService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PpeportalTestModule],
                declarations: [CUSTSTATUSComponent],
                providers: []
            })
                .overrideTemplate(CUSTSTATUSComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CUSTSTATUSComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CUSTSTATUSService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CUSTSTATUS(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.cUSTSTATUSES[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
