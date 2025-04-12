import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMeta } from 'app/entities/meta/meta.model';
import { MetaService } from 'app/entities/meta/service/meta.service';
import { IAluno } from '../aluno.model';
import { AlunoService } from '../service/aluno.service';
import { AlunoFormGroup, AlunoFormService } from './aluno-form.service';

@Component({
  selector: 'jhi-aluno-update',
  templateUrl: './aluno-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AlunoUpdateComponent implements OnInit {
  isSaving = false;
  aluno: IAluno | null = null;

  metasCollection: IMeta[] = [];

  protected alunoService = inject(AlunoService);
  protected alunoFormService = inject(AlunoFormService);
  protected metaService = inject(MetaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AlunoFormGroup = this.alunoFormService.createAlunoFormGroup();

  compareMeta = (o1: IMeta | null, o2: IMeta | null): boolean => this.metaService.compareMeta(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aluno }) => {
      this.aluno = aluno;
      if (aluno) {
        this.updateForm(aluno);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aluno = this.alunoFormService.getAluno(this.editForm);
    if (aluno.id !== null) {
      this.subscribeToSaveResponse(this.alunoService.update(aluno));
    } else {
      this.subscribeToSaveResponse(this.alunoService.create(aluno));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAluno>>): void {
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

  protected updateForm(aluno: IAluno): void {
    this.aluno = aluno;
    this.alunoFormService.resetForm(this.editForm, aluno);

    this.metasCollection = this.metaService.addMetaToCollectionIfMissing<IMeta>(this.metasCollection, aluno.meta);
  }

  protected loadRelationshipsOptions(): void {
    this.metaService
      .query({ filter: 'aluno-is-null' })
      .pipe(map((res: HttpResponse<IMeta[]>) => res.body ?? []))
      .pipe(map((metas: IMeta[]) => this.metaService.addMetaToCollectionIfMissing<IMeta>(metas, this.aluno?.meta)))
      .subscribe((metas: IMeta[]) => (this.metasCollection = metas));
  }
}
