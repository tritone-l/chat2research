import { FC, useRef } from "react";
import styles from "./index.module.css"
import { usePageStore } from "../../store";
import FilePDF from "../FilePDF";


const Preview: FC = () => {
    const selectedFile = usePageStore((state) => state.selectedFile);
    const fileList = usePageStore((state) => state.fileList);
    const containerRef = useRef<any>(null);

    return <>
        {
            fileList.map(file => {
                const isSelect = file.path === selectedFile?.path;
                return <div key={file.path} className={styles.container} ref={containerRef} style={{
                    display: isSelect ? 'block' : 'none'
                }}>
                    {
                        selectedFile && <FilePDF file={selectedFile} />
                    }
                </div >
            })
        }
    </>
}

export default Preview;