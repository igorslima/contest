package ufc.quixada.npi.contest.service;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.contest.model.StorageProperties;
import ufc.quixada.npi.contest.util.Constants;
import ufc.quixada.npi.contest.validator.StorageException;
import ufc.quixada.npi.contest.validator.StorageFileNotFoundException;

@Service
public class FileSystemStorageService implements StorageService{
	
	private final Path rootLocation;
	
	@Autowired
	private MessageService messsagemService;
	
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }
    
   
    @Override
	public String store(MultipartFile arquivoUpload, String pastaDeDestino) throws IOException {
			 
		 	String caminho = new StringBuilder(Constants.CAMINHO_TRABALHOS)
		 			.append(File.separator)
		 			.append(pastaDeDestino)
		 			.append(File.separator)			 			
		 			.toString();
		 	
            if (arquivoUpload.isEmpty()) {
                throw new StorageException(messsagemService.getMessage("ARQUIVO_VAZIO"));
            }
            
            File pasta = new File(caminho);
            if(!pasta.exists() && !pasta.mkdirs()){
        		throw new RuntimeException("Destino inexistente");
            }
            SimpleDateFormat dataFormat = new SimpleDateFormat("-ddMMyy-HHmmss-SSS");
            
            String caminhoDoArquivo = new StringBuilder(caminho)
            		.append(pastaDeDestino)
            		.append(dataFormat.format(new Date()))
            		.append(".pdf")
            		.toString();
            
			Files.copy(arquivoUpload.getInputStream(), Paths.get(caminhoDoArquivo), REPLACE_EXISTING);
			return caminhoDoArquivo;
				
	}

    @Override
	public Stream<Path> loadAll() {
		try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException(messsagemService.getMessage("FALHA_AO_LER_ARQUIVO"));
        }
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
            	throw new StorageFileNotFoundException(messsagemService.getMessage("FALHA_AO_LER_ARQUIVO"));

            }
        } catch (MalformedURLException e) {
        	throw new StorageFileNotFoundException(messsagemService.getMessage("FALHA_AO_LER_ARQUIVO"));
        }
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
		
	}

	@Override
	public File getFile(String path) {
		File file = new File(path);
		return file;
	}


	@Override
	public void deleteArquivo(String path) {
		File caminho = new File(path);
		caminho.delete();
	}


}