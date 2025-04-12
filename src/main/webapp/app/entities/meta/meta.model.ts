export interface IMeta {
  id: number;
  linguagens?: number | null;
  humanas?: number | null;
  natureza?: number | null;
  matematica?: number | null;
}

export type NewMeta = Omit<IMeta, 'id'> & { id: null };
