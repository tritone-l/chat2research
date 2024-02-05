import { FC, useCallback, useEffect, useMemo } from "react";
import styles from "./index.module.css"
import Sidebar from "./components/Sidebar";
import Preview from "./components/Preview";
import { usePageStore } from "./store";

const MainPage: FC = () => {
  const addFile = usePageStore((state) => state.addFile);
  const updateSelectedFile = usePageStore((state) => state.updateSelectedFile);

  useMemo(() => {
    // @ts-ignore
    window.electronAPI.onChangeFile((value) => {
      addFile(value);
      updateSelectedFile(value);
    })
  }, [])

  return <div className={styles.page}>
    <div className={styles.page__sidebar}>
      <Sidebar />
    </div>
    <div className={styles.page__preview}>
      <Preview />
    </div>
  </div>
}

export default MainPage;