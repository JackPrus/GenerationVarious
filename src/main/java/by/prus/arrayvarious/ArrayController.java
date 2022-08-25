package by.prus.arrayvarious;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/array")
public class ArrayController {
    @Autowired
    ArrayService arrayService;

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public List<List<String>> createVarious(@RequestBody String[][] arrays) throws Exception {
        return arrayService.generateVariousByReadyArray(arrays);
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public List<List<String>> generateAndCreateVarious(
            @RequestParam(value = "row", defaultValue = "5") int row,
            @RequestParam(value = "column", defaultValue = "5") int column
    ) throws Exception {
        return arrayService.createArrAndGenerateVarious(row, column);
    }
}
