
import { createContext } from 'react';
import type { StoreApi } from 'zustand';
import { create } from 'zustand';

export interface FileItem {
  path: string;
  name: string;
  type: 'file' | 'arraybuffer'
  data: any;
}

export interface StoreType {
  count: number;
  fileList: FileItem[];
  selectedFile: FileItem | null;
  add: () => void;
  addFile: (file: FileItem) => void;
  updateSelectedFile: (file: FileItem) => void;
}

export const ProductDetailContext = createContext<StoreApi<StoreType> | null>(null);

export const usePageStore = create<StoreType>((set, get) => ({
  count: 1,
  add: () => {
    set({
      count: get().count + 1
    })
  },
  fileList: [],
  selectedFile: null,
  addFile: (file: FileItem) => {
    const { fileList } = get();
    const isExist = fileList.find(_file => _file.path === file.path);
    if (isExist) {
      return console.error("已经存在该文件");
      // throw Error("已经存在该文件")
    }
    const list = [...fileList, file];
    set({
      fileList: list
    })
  },
  updateSelectedFile: (file) => set({
    selectedFile: file
  })
}));

// const useBearStore = create((set) => ({
//     bears: 0,
//     increasePopulation: () => set((state) => ({ bears: state.bears + 1 })),
//     removeAllBears: () => set({ bears: 0 }),
//   }))

// export const useProductDetailStore = () => {
//     return useStore(store);
// };
