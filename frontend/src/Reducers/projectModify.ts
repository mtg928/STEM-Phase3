import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'

export interface SelectedProjectTypes {
  projects: Array<number>
}

const initialState: SelectedProjectTypes = {
  projects: []
}

export const projectModifier = createSlice({
  name: 'modifier',
  initialState,
  reducers: {
    add: (state, action: PayloadAction<number>) => {
      if (!state.projects.includes(action.payload)) {
        state.projects.push(action.payload)
      }
    },
    remove: (state, action: PayloadAction<number>) => {
      const index = state.projects.indexOf(action.payload)
      if (index !== -1) {
        state.projects.splice(index, 1)
      }
    },
    removeAll: (state) => {
      state.projects = []
    },
  },
})

export const { add, remove, removeAll } = projectModifier.actions
export default projectModifier.reducer