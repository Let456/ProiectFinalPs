import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import image from './profile.png'
import background from './fundal.jpg'
import {InputAdornment} from "@mui/material";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import { DataGrid, GridColDef, GridValueGetterParams } from '@mui/x-data-grid';
import {useEffect, useState} from "react";
import { useLocation } from 'react-router-dom';
import {useTranslation} from "react-i18next";
import { useStyles } from './Manager.styles';

interface Parfum {
    parfumId: number;
    nume: string;
    producator: string;
  }
  
  interface SticlaParfum {
    sticlaParfumId: number;
    volum: number;
    pret: number;
    parfum: Parfum;
  }
  
  interface Magazin {
    magazinId: number;
  }
  
  interface ParfumMagazin {
    parfumMagazinId: number;
    disponibilitate: number;
    sticlaParfum: SticlaParfum;
    magazin: Magazin;
  }
  
  const columns: GridColDef[] = [
    { field: 'nume', headerName: 'Nume', width: 150 },
    { field: 'producator', headerName: 'Producator', width: 150 },
    { field: 'pret', headerName: 'Pret', width: 150 },
    {
      field: 'disponibilitate',
      headerName: 'Disponibilitate',
      type: 'number',
      width: 150,
    },
    {
      field: 'volum',
      headerName: 'Volum',
      width: 150,
    },
  ];
  
  export const Angajat = (): JSX.Element => {  
    const [parfumMagazine, setParfumMagazine] = useState<ParfumMagazin[]>([]);
    const [selectedParfumMagazine, setSelectedParfumMagazine] = React.useState<ParfumMagazin | null>(null);
  
    const location = useLocation();
    
    const id = parseInt(location.state?.key, 10);
    console.log(id);
  
    const [nume, setNume] = React.useState<string>("");
    const [producator, setProducator] = React.useState<string>("");
    const [disponibilitate, setDisponibilitate] = React.useState<string>("");
    const [volum, setVolum] = React.useState<string>("");
    const [pret, setPret] = React.useState<string>("");
    
    const [t, i18n] = useTranslation("global");
  
  
    const handleChangeLanguage = (lang: string) => {
        i18n.changeLanguage(lang);
    };
  
  
    const onChangeNume = (event: any): void => {
      setNume(event.target.value)
    }
  
    const onChangeProducator = (event: any): void => {
      setProducator(event.target.value)
    }
  
    const onChangeDisponibilitate = (event: any): void => {
      setDisponibilitate(event.target.value)
    }
  
    const onChangeVolum = (event: any): void => {
      setVolum(event.target.value)
    }
  
    const onChangePret = (event: any): void => {
      setPret(event.target.value)
    }
  
    useEffect(() => {
      axios.post('http://localhost:8060/ParfumMagazin/FindAllFrom', id, {headers: {"Content-Type": "application/json"
          }}).then(response => {
              setParfumMagazine(response.data)
          })
          .catch(error => {
              console.error(error.response.data);
              alert(`Error: ${error.response.data}`); 
      });
    }, []);
  
    
  
    const handleSelectionChange = (selectionModel: any): void => {
      if (selectionModel.length > 0) {
        const   selectedParfumMagazinId = selectionModel[0]; 
        const parfumMagazin = parfumMagazine.find((c) => c.parfumMagazinId === selectedParfumMagazinId) || null;
        setSelectedParfumMagazine(parfumMagazin);
        console.log("Selected parfumMagazin", parfumMagazin);
      } else {
        setSelectedParfumMagazine(null);
        console.log("No row selected");
      }
    };
  
    const onInsert = (): void => {
      axios.post('http://localhost:8060/User/GetMagazinId',  id , { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          const magazinId = response.data;
    
          const newParfumMagazin: ParfumMagazin = {
            parfumMagazinId: 0,
            disponibilitate: parseInt(disponibilitate),
            sticlaParfum: {
              sticlaParfumId: 0,
              volum: parseInt(volum),
              pret: parseFloat(pret),
              parfum: {
                parfumId: 0,
                nume: nume,
                producator: producator,
              },
            },
            magazin: {
              magazinId: magazinId,
            },
          };
    
          axios.post('http://localhost:8060/ParfumMagazin/Insert', newParfumMagazin, { headers: { 'Content-Type': 'application/json' } })
            .then(response => {
              console.log(response.data);
              setParfumMagazine([...parfumMagazine, newParfumMagazin]);
            })
            .catch(error => {
              console.error(error.response.data);
              alert(`Error: ${error.response.data}`);
            });
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
    
    const onUpdate = (): void => {
      if (!selectedParfumMagazine) return;
    
      axios.post('http://localhost:8060/User/GetMagazinId',  id , { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          const magazinId = response.data;
    
          const updatedParfumMagazin = {
            ...selectedParfumMagazine,
            disponibilitate: parseInt(disponibilitate) || selectedParfumMagazine.disponibilitate,
            sticlaParfum: {
              ...selectedParfumMagazine.sticlaParfum,
              volum: parseInt(volum) || selectedParfumMagazine.sticlaParfum.volum,
              pret: parseFloat(pret) || selectedParfumMagazine.sticlaParfum.pret,
              parfum: {
                ...selectedParfumMagazine.sticlaParfum.parfum,
                nume: nume || selectedParfumMagazine.sticlaParfum.parfum.nume,
                producator: producator || selectedParfumMagazine.sticlaParfum.parfum.producator,
              },
            },
            magazin: {
              magazinId: magazinId,
            },
          };
    
          axios.post('http://localhost:8060/ParfumMagazin/Insert', updatedParfumMagazin, { headers: { 'Content-Type': 'application/json' } })
            .then(response => {
              console.log(response.data);
              setParfumMagazine(parfumMagazine.map(pm => pm.parfumMagazinId === selectedParfumMagazine.parfumMagazinId ? updatedParfumMagazin : pm));
            })
            .catch(error => {
              console.error(error.response.data);
              alert(`Error: ${error.response.data}`);
            });
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
    
    const onDelete = (): void => {
      if (!selectedParfumMagazine) return;
  
      axios.post('http://localhost:8060/ParfumMagazin/Delete', selectedParfumMagazine, { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          console.log(response.data);
          setParfumMagazine(parfumMagazine.filter(pm => pm.parfumMagazinId !== selectedParfumMagazine.parfumMagazinId));
          setSelectedParfumMagazine(null);
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
  
    const saveAsCsv = () => {
      axios.post('http://localhost:8060/User/SaveAsCsv', id, { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          alert(response.data);
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
  
    const saveAsJson = () => {
      axios.post('http://localhost:8060/User/SaveAsJson', id, { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          alert(response.data);
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
  
    const saveAsDoc = () => {
      axios.post('http://localhost:8060/User/SaveAsDoc', id, { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          alert(response.data);
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
  
    const saveAsXml = () => {
      axios.post('http://localhost:8060/User/SaveAsXml', id, { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          alert(response.data);
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
  
    const onSearch = (): void => {
      axios.post('http://localhost:8060/ParfumMagazin/Search',  { name: nume }, { headers: { 'Content-Type': 'application/json' } })
        .then(response => {
          console.log(response.data);
          setParfumMagazine(response.data);
        })
        .catch(error => {
          console.error(error.response.data);
          alert(`Error: ${error.response.data}`);
        });
    };
  
  
    const rows = parfumMagazine.map((pm) => ({
      id: pm.parfumMagazinId,
      nume: pm.sticlaParfum.parfum.nume,
      producator: pm.sticlaParfum.parfum.producator,
      pret: pm.sticlaParfum.pret,
      disponibilitate: pm.disponibilitate,
      volum: pm.sticlaParfum.volum,
    }));
  
    return (
      <div className={'name'}>
        <DataGrid
          rows={rows}
          columns={columns}
          onRowSelectionModelChange={handleSelectionChange}
          checkboxSelection
        />
        <Box display="flex" justifyContent="space-between" mb={2}>
        <TextField label={t('Nume')}  value={nume} onChange={onChangeNume} />
        <TextField label={t('Producator')} value={producator} onChange={onChangeProducator} />
        <TextField label={t('Disponibilitate')} value={disponibilitate} onChange={onChangeDisponibilitate} />
        <TextField label={t('Volum')} value={volum} onChange={onChangeVolum} />
        <TextField label={t('Pret')} value={pret} onChange={onChangePret} />
        </Box>
        <Box display="flex" flexDirection="column" mb={2}>
          <Box display="flex" justifyContent="space-between" mb={2}>
            <Button onClick={onInsert}>{t('Insert')}</Button>
            <Button onClick={onUpdate} disabled={!selectedParfumMagazine}>{t('Update')}</Button>
            <Button onClick={onDelete} disabled={!selectedParfumMagazine}>{t('Delete')}</Button>
            <Button variant="contained" onClick={onSearch}>{t('Search')}</Button>
          </Box>
          <Box display="flex" justifyContent="space-between">
          <Button variant="contained" onClick={saveAsCsv}>{t('SaveAsCsv')}</Button>
          <Button variant="contained" onClick={saveAsDoc}>{t('SaveAsJson')}</Button>
          <Button variant="contained" onClick={saveAsJson}> {t('SaveAsDoc')}</Button>
          <Button variant="contained" onClick={saveAsXml}>{t('SaveAsXml')}</Button>
          <Button variant="contained" onClick={() => handleChangeLanguage("en")}>English</Button>
          <Button variant="contained" onClick={() => handleChangeLanguage("ro")}>Română</Button>
          <Button variant="contained" onClick={() => handleChangeLanguage("fr")}>Français</Button>
          <Button variant="contained" onClick={() => handleChangeLanguage("de")}>Deutsch</Button>
          </Box>
        </Box>
      </div>
    );
  }
  
  export default Angajat;
  
