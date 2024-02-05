import { FC, useRef, useState } from "react";
import styles from "./index.module.css"
import { usePageStore } from "../../store";
import FilePDF from "../FilePDF";


const Preview: FC = () => {
  const [scaleValue, setScaleValue] = useState(1);
  const selectedFile = usePageStore((state) => state.selectedFile);
  const fileList = usePageStore((state) => state.fileList);
  const containerRef = useRef<any>(null);

  return <div className={styles.container}>
    <div className={styles.scale}>
      <button className={styles.scale__btn} onClick={() => {
        setScaleValue(scaleValue + 0.2)
      }}>
        放大
      </button>
      <button className={styles.scale__btn} onClick={() => {
        setScaleValue(scaleValue - 0.2)
      }}>
        缩小
      </button>
    </div>
    <div className={styles.content} style={{
      transform: `scale(${scaleValue})`
    }}>
      {
        fileList.map(file => {
          const isSelect = file.path === selectedFile?.path;
          return <div key={file.path} className={styles.content__file} ref={containerRef} style={{
            display: isSelect ? 'block' : 'none'
          }}>
            {
              selectedFile && <FilePDF file={selectedFile} />
            }
          </div >
        })
      }
    </div>
  </div>
}

export default Preview;