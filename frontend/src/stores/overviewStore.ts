import { observable, action, makeAutoObservable, computed } from "mobx";

export class OverviewStore {
  selectedProjects: Array<number> = []

  constructor() {
    makeAutoObservable(this, {
      selectedProjects: observable,
      setSelectedProjects: action,
      removeSelectedProjects: action,
      removeAll: action,
      selectedProjectsList: computed,
    })
  }

  setSelectedProjects = (id: number) => {
    if (!this.selectedProjects.includes(id)) {
      this.selectedProjects.push(id)
    }
  }

  removeSelectedProjects = (id: number) => {
    const index = this.selectedProjects.indexOf(id)
    if (index !== -1) {
      this.selectedProjects.splice(index, 1)
    }
  }

  removeAll = () => {
    this.selectedProjects.splice(0, this.selectedProjects.length)
  }

  get selectedProjectsList(): Array<number> {
    return this.selectedProjects
  }
}