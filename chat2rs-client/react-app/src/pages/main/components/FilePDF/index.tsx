import { FC, useEffect, useRef, useState } from 'react'
import * as PDFJS from 'pdfjs-dist'

import styles from "./index.module.css"
import React from 'react';

PDFJS.GlobalWorkerOptions.workerSrc = './pdf.worker.mjs';

interface Props {
    file: File
}

const FilePDF: FC<Props> = ({ file }) => {
    const [fileData, setFileData] = useState<any>(null);
    const [pdfContainers, setPdfContainers] = useState<any[]>([]);
    const pdfDoc = useRef<any>()
    const pdfNumPages = useRef(0)

    const convertFileToArrayBuffer = (file: File) => {
        if (file) {
            const reader = new FileReader();
            reader.onload = function (event) {
                // @ts-ignore
                const arrayBuffer = event.target.result;
                setFileData(arrayBuffer);
            };
            reader.readAsArrayBuffer(file);
        } else {
            console.error('Please select a file to convert to ArrayBuffer!');
        }
    }

    // 依次渲染所有页面
    const renderPage = (num: number) => {
        pdfDoc.current!.getPage(num).then((page: any) => {
            const viewport = page.getViewport({ scale: 10 })
            const pdfContainer = pdfContainers[num - 1];
            pdfContainer.current!.width = viewport.width
            pdfContainer.current!.height = viewport.height

            page
                .render({
                    viewport,
                    canvasContext: pdfContainer.current!.getContext('2d')
                })
                .promise.then(() => {
                    if (num < pdfNumPages.current) {
                        renderPage(num + 1)
                    }
                })
        })
    }

    useEffect(() => {
        convertFileToArrayBuffer(file);
    }, [file])

    useEffect(() => {
        // pdfCtx.current = pdfContainer.current!.getContext('2d');
        if (fileData) {
            PDFJS.getDocument(fileData).promise.then(pdfDoc_ => {
                const num = pdfDoc_.numPages;
                const list = new Array(num).fill('').map(() => React.createRef());
                setPdfContainers(list)
                pdfDoc.current = pdfDoc_;
                pdfNumPages.current = pdfDoc_.numPages;

            })
        }
    }, [fileData])

    useEffect(() => {
        if (pdfContainers.length) {
            renderPage(1)
        }
    }, [pdfContainers])


    return (
        <div className={styles.container}>
            {
                pdfContainers.map((item, index) => (
                    <canvas key={index} className={styles.canvas} ref={item}></canvas>
                ))
            }
        </div>
    )
}

export default FilePDF
