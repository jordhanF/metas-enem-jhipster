import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';
import { finalize, delay, map } from 'rxjs/operators';
import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IAluno } from 'app/entities/aluno/aluno.model';
import { AlunoService } from 'app/entities/aluno/service/aluno.service';
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
  alunosSharedCollection: IAluno[] = [];

  constructor(
    protected metaService: MetaService,
    protected metaFormService: MetaFormService,
    protected alunoService: AlunoService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  editForm: MetaFormGroup = this.metaFormService.createMetaFormGroup();

  compareAluno = (o1: IAluno | null, o2: IAluno | null): boolean => this.alunoService.compareAluno(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meta }) => {
      this.meta = meta;
      if (meta) {
        this.updateForm(meta);
      }
      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;

    // Simula uma chamada HTTP bem-sucedida
    of(null)
      .pipe(
        delay(1000), // Simula delay de rede
        finalize(() => {
          this.isSaving = false;
          this.showSuccessToast();
          this.previousState();
        }),
      )
      .subscribe();

    // Comente a chamada real ao servidor
    // const meta = this.metaFormService.getMeta(this.editForm);
    // if (meta.id !== null) {
    //   this.subscribeToSaveResponse(this.metaService.update(meta));
    // } else {
    //   this.subscribeToSaveResponse(this.metaService.create(meta));
    // }
  }

  private showSuccessToast(): void {
    const toast = document.createElement('div');
    toast.style.position = 'fixed';
    toast.style.bottom = '20px';
    toast.style.right = '20px';
    toast.style.padding = '12px 24px';
    toast.style.background = '#28a745';
    toast.style.color = 'white';
    toast.style.borderRadius = '4px';
    toast.style.boxShadow = '0 2px 10px rgba(0,0,0,0.1)';
    toast.style.zIndex = '1000';
    toast.style.transition = 'all 0.3s ease';
    toast.innerText = 'Meta salva com sucesso!';

    document.body.appendChild(toast);

    setTimeout(() => {
      toast.style.opacity = '0';
      setTimeout(() => toast.remove(), 300);
    }, 3000);
  }

  protected updateForm(meta: IMeta): void {
    this.meta = meta;
    this.metaFormService.resetForm(this.editForm, meta);
    this.alunosSharedCollection = this.alunoService.addAlunoToCollectionIfMissing<IAluno>(this.alunosSharedCollection, meta.aluno);
  }

  protected loadRelationshipsOptions(): void {
    this.alunoService
      .query()
      .pipe(map((res: HttpResponse<IAluno[]>) => res.body ?? []))
      .pipe(map((alunos: IAluno[]) => this.alunoService.addAlunoToCollectionIfMissing<IAluno>(alunos, this.meta?.aluno)))
      .subscribe((alunos: IAluno[]) => {
        this.alunosSharedCollection = alunos;
      });
  }
}
