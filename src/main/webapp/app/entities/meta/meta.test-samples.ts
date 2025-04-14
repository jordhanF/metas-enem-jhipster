import dayjs from 'dayjs/esm';

import { IMeta, NewMeta } from './meta.model';

export const sampleWithRequiredData: IMeta = {
  id: 17187,
};

export const sampleWithPartialData: IMeta = {
  id: 25004,
  linguagens: 446,
  natureza: 142,
};

export const sampleWithFullData: IMeta = {
  id: 32703,
  linguagens: 176,
  humanas: 12,
  natureza: 904,
  matematica: 348,
  dataMeta: dayjs('2025-04-12'),
};

export const sampleWithNewData: NewMeta = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
