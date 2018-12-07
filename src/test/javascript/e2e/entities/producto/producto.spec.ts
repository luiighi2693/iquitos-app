/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductoComponentsPage, ProductoDeleteDialog, ProductoUpdatePage } from './producto.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Producto e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productoUpdatePage: ProductoUpdatePage;
    let productoComponentsPage: ProductoComponentsPage;
    let productoDeleteDialog: ProductoDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Productos', async () => {
        await navBarPage.goToEntity('producto');
        productoComponentsPage = new ProductoComponentsPage();
        expect(await productoComponentsPage.getTitle()).to.eq('Productos');
    });

    it('should load create Producto page', async () => {
        await productoComponentsPage.clickOnCreateButton();
        productoUpdatePage = new ProductoUpdatePage();
        expect(await productoUpdatePage.getPageTitle()).to.eq('Create or edit a Producto');
        await productoUpdatePage.cancel();
    });

    it('should create and save Productos', async () => {
        const nbButtonsBeforeCreate = await productoComponentsPage.countDeleteButtons();

        await productoComponentsPage.clickOnCreateButton();
        await promise.all([
            productoUpdatePage.setCodigoInput('codigo'),
            productoUpdatePage.setNombreInput('nombre'),
            productoUpdatePage.setDescripcionInput('descripcion'),
            productoUpdatePage.setFechaExpiracionInput('2000-12-31'),
            productoUpdatePage.setImagenInput(absolutePath),
            productoUpdatePage.setStockInput('5'),
            productoUpdatePage.setNotificacionDeLimiteDeStockInput('5'),
            productoUpdatePage.tipoDeProductoSelectLastOption(),
            productoUpdatePage.unidadDeMedidaSelectLastOption(),
            productoUpdatePage.categoriaSelectLastOption()
            // productoUpdatePage.variantesSelectLastOption(),
        ]);
        expect(await productoUpdatePage.getCodigoInput()).to.eq('codigo');
        expect(await productoUpdatePage.getNombreInput()).to.eq('nombre');
        expect(await productoUpdatePage.getDescripcionInput()).to.eq('descripcion');
        expect(await productoUpdatePage.getFechaExpiracionInput()).to.eq('2000-12-31');
        const selectedEsFavorito = productoUpdatePage.getEsFavoritoInput();
        if (await selectedEsFavorito.isSelected()) {
            await productoUpdatePage.getEsFavoritoInput().click();
            expect(await productoUpdatePage.getEsFavoritoInput().isSelected()).to.be.false;
        } else {
            await productoUpdatePage.getEsFavoritoInput().click();
            expect(await productoUpdatePage.getEsFavoritoInput().isSelected()).to.be.true;
        }
        const selectedVisibleParaLaVenta = productoUpdatePage.getVisibleParaLaVentaInput();
        if (await selectedVisibleParaLaVenta.isSelected()) {
            await productoUpdatePage.getVisibleParaLaVentaInput().click();
            expect(await productoUpdatePage.getVisibleParaLaVentaInput().isSelected()).to.be.false;
        } else {
            await productoUpdatePage.getVisibleParaLaVentaInput().click();
            expect(await productoUpdatePage.getVisibleParaLaVentaInput().isSelected()).to.be.true;
        }
        expect(await productoUpdatePage.getImagenInput()).to.endsWith(fileNameToUpload);
        expect(await productoUpdatePage.getStockInput()).to.eq('5');
        expect(await productoUpdatePage.getNotificacionDeLimiteDeStockInput()).to.eq('5');
        await productoUpdatePage.save();
        expect(await productoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Producto', async () => {
        const nbButtonsBeforeDelete = await productoComponentsPage.countDeleteButtons();
        await productoComponentsPage.clickOnLastDeleteButton();

        productoDeleteDialog = new ProductoDeleteDialog();
        expect(await productoDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Producto?');
        await productoDeleteDialog.clickOnConfirmButton();

        expect(await productoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
