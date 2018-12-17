/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PpeportalTestModule } from '../../../test.module';
import { CUSTSTATUSDeleteDialogComponent } from 'app/entities/custstatus/custstatus-delete-dialog.component';
import { CUSTSTATUSService } from 'app/entities/custstatus/custstatus.service';

describe('Component Tests', () => {
    describe('CUSTSTATUS Management Delete Component', () => {
        let comp: CUSTSTATUSDeleteDialogComponent;
        let fixture: ComponentFixture<CUSTSTATUSDeleteDialogComponent>;
        let service: CUSTSTATUSService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PpeportalTestModule],
                declarations: [CUSTSTATUSDeleteDialogComponent]
            })
                .overrideTemplate(CUSTSTATUSDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CUSTSTATUSDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CUSTSTATUSService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
