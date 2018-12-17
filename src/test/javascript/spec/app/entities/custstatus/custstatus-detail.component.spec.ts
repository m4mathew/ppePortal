/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PpeportalTestModule } from '../../../test.module';
import { CUSTSTATUSDetailComponent } from 'app/entities/custstatus/custstatus-detail.component';
import { CUSTSTATUS } from 'app/shared/model/custstatus.model';

describe('Component Tests', () => {
    describe('CUSTSTATUS Management Detail Component', () => {
        let comp: CUSTSTATUSDetailComponent;
        let fixture: ComponentFixture<CUSTSTATUSDetailComponent>;
        const route = ({ data: of({ cUSTSTATUS: new CUSTSTATUS(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PpeportalTestModule],
                declarations: [CUSTSTATUSDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CUSTSTATUSDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CUSTSTATUSDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cUSTSTATUS).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
