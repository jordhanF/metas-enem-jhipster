import dayjs from 'dayjs/esm';
import { IMeta } from 'app/entities/meta/meta.model';

export interface IAluno {
  id: number;
  nome?: string | null;
  email?: string | null;
  dataNascimento?: dayjs.Dayjs | null;
  meta?: Pick<IMeta, 'id'> | null;
}

export type NewAluno = Omit<IAluno, 'id'> & { id: null };
