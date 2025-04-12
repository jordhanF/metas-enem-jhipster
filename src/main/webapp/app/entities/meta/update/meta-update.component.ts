import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMeta } from '../meta.model';
import { MetaService } from '../service/meta.service';
import { MetaFormGroup, MetaFormService } from './meta-form.service';

@Component({
  selector: 'jhi-meta-update',
  templateUrl: './meta-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MetaUpdateComponent implements OnInit {
  isSaving = false;
  meta: IMeta | null = null;

  protected metaService = inject(MetaService);
  protected metaFormService = inject(MetaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MetaFormGroup = this.metaFormService.createMetaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meta }) => {
      this.meta = meta;
      if (meta) {
        this.updateForm(meta);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const meta = this.metaFormService.getMeta(this.editForm);
    if (meta.id !== null) {
      this.subscribeToSaveResponse(this.metaService.update(meta));
    } else {
      this.subscribeToSaveResponse(this.metaService.create(meta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeta>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(meta: IMeta): void {
    this.meta = meta;
    this.metaFormService.resetForm(this.editForm, meta);
  }
}
