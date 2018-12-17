/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PpeportalTestModule } from '../../../test.module';
import { CUSTSTATUSUpdateComponent } from 'app/entities/custstatus/custstatus-update.component';
import { CUSTSTATUSService } from 'app/entities/custstatus/custstatus.service';
import { CUSTSTATUS } from 'app/shared/model/custstatus.model';

describe('Component Tests', () => {
    describe('CUSTSTATUS Management Update Component', () => {
        let comp: CUSTSTATUSUpdateComponent;
        let fixture: ComponentFixture<CUSTSTATUSUpdateComponent>;
        let service: CUSTSTATUSService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PpeportalTestModule],
                declarations: [CUSTSTATUSUpdateComponent]
            })
                .overrideTemplate(CUSTSTATUSUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CUSTSTATUSUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CUSTSTATUSService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CUSTSTATUS(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cUSTSTATUS = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CUSTSTATUS();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cUSTSTATUS = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
